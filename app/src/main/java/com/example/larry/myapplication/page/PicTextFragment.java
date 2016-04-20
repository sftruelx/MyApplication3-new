package com.example.larry.myapplication.page;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.base.RequestQueue;
import com.android.volley.cache.plus.ImageLoader;
import com.example.larry.myapplication.R;
import com.example.larry.myapplication.entity.Album;
import com.example.larry.myapplication.songList.SongDetailActivity;
import com.example.larry.myapplication.utils.AppUrl;
import com.example.larry.myapplication.utils.LogHelper;
import com.example.larry.myapplication.utils.UILApplication;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 067231 on 2016/1/14.
 */
public class PicTextFragment extends Fragment {
    public RequestQueue mQueue;
    private ImageLoader mImageLoader;
    private static final String Frame_list = "frame_list";
    private static final String Frame_Title = "frame_title";
    private static final String Frame_id = "frame_id";
    private String title = "";
    public static PicTextFragment newInstance(String title, int id, ArrayList<Album> albumList){
        PicTextFragment pt = new PicTextFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(Frame_list, albumList);
        args.putString(Frame_Title,title);
        args.putInt(Frame_id, id);
        pt.setArguments(args);
        return pt;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueue = ((UILApplication)getActivity().getApplication()).mQueue;
        mImageLoader = new ImageLoader(mQueue);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_one_column_content,container,false);
        TextView tv_title = (TextView)rootView.findViewById(R.id.column_title);
        title= getArguments().getString(Frame_Title);
        tv_title.setText(title);
        View btnMore = rootView.findViewById(R.id.btn_more);
        btnMore.setTag(title);
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出列表窗口
                LogHelper.i("test",v.getTag());
                int classifyId = getArguments().getInt(Frame_id);
                String title = getArguments().getString(Frame_Title);
                Intent intent = new Intent(v.getContext(), ShowListActivity.class);
                intent.putExtra("classifyId",classifyId);
                intent.putExtra("title",title);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });

        List<Album> list = getArguments().getParcelableArrayList(Frame_list);
        LinearLayout linear = (LinearLayout) rootView.findViewById(R.id.tab_one_column_linear);

//        linear.setOrientation(LinearLayout.HORIZONTAL);//设置为水平
        for(final Album album : list){
            LogHelper.i("=========",album.getAlbumName());
            View child = inflater.inflate(R.layout.pic_text,container,false);
            TextView mTitle= (TextView)child.findViewById(R.id.pic_title);
            mTitle.setText(album.getAlbumName());
            child.setTag(album.getId());
            child.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 2.0f));
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText( getContext() , "你选择的是: "+v.getTag().toString(), Toast.LENGTH_SHORT).show();
                }
            });
            final ImageView mListImg = (ImageView)child.findViewById(R.id.pic_image1);
            ImageLoader.ImageListener listener = ImageLoader.getImageListener(mListImg, android.R.drawable.ic_menu_rotate, android.R.drawable.ic_delete);
            mImageLoader.get(AppUrl.webUrl + album.getImgPath(), listener);
            linear.addView(child);
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, SongDetailActivity.class);
                    intent.putExtra(SongDetailActivity.ARG_ITEM_ID, album);
                    Bitmap bmp = ((BitmapDrawable) mListImg.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] bitmapByte = baos.toByteArray();
                    intent.putExtra("bitmap", bitmapByte);
                    context.startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
                }
            });
        }


        return rootView;
    }
}
