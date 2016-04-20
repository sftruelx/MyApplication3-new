package com.example.larry.myapplication.page;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Receiver;
import com.android.volley.TaskHandle;
import com.example.larry.myapplication.R;
import com.example.larry.myapplication.banner.SimpleImageBanner;
import com.example.larry.myapplication.entity.Album;
import com.example.larry.myapplication.entity.DataModule;
import com.example.larry.myapplication.network.NetworkModule;
import com.example.larry.myapplication.swipe.ProgressFragment;
import com.example.larry.myapplication.swipe.SwipeRefreshLayout;
import com.example.larry.myapplication.utils.DataProvider;
import com.example.larry.myapplication.utils.MyFragment;
import com.example.larry.myapplication.utils.T;
import com.example.larry.myapplication.utils.UILApplication;
import com.example.larry.myapplication.utils.ViewFindUtils;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by 067231 on 2016/1/14.
 */
public class TabOneFragment extends ProgressFragment implements Receiver<DataModule> {

    private View mContentView;

    public static TabOneFragment newInstance() {
        TabOneFragment tabone = new TabOneFragment();
        return tabone;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.tab_one, container, false);
        sib_simple_usage(mContentView);
//        TextView tv = (TextView) mContentView.findViewById(R.id.tab1_title);
//        tv.setText(new SimpleDateFormat("dd-MMM EEEE aa", Locale.SIMPLIFIED_CHINESE).format(new Date()));

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void sib_simple_usage(final View view) {
        SimpleImageBanner sib = ViewFindUtils.find(view, R.id.sib_simple_usage);

        sib
                .setSource(DataProvider.getList())
                .startScroll();

        sib.setOnItemClickL(new SimpleImageBanner.OnItemClickL() {
            @Override
            public void onItemClick(int position) {
                T.showShort(view.getContext(), "position--->" + position);
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Setup content view
        setContentView(mContentView);
        // Setup text for empty content
        setEmptyText(R.string.empty);
        setContentShown(false);
        obtainData(64);
        obtainData(65);
        obtainData(66);
        obtainData(67);
        obtainData(68);
        obtainData(69);
        obtainData(70);
        obtainData(71);
        obtainData(72);

    }

    private void obtainData(int type) {
        TaskHandle handle_0 = getNetworkModule().getAlbums(type);
        handle_0.setId(type);
        handle_0.setReceiver(this);
        handle_0.pullTrigger();
    }

    @Override
    public void onSucess(TaskHandle handle, DataModule result) {
        switch (handle.id()) {

            case 65:
                try {
                    if (result.code() > 0) {
                        getFragmentManager().beginTransaction().add(R.id.tab_one_frame_one, PicTextFragment.newInstance("必读经典", 65, result.getAlbum())).commit();
                       }
                    setContentShown(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 64:
                try {
                    if (result.code() > 0) {
                        getFragmentManager().beginTransaction().add(R.id.tab_one_frame_zone, PicTextFragment.newInstance("编辑推荐", 64, result.getAlbum())).commit();
                    }
                    setContentShown(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 66:
                try {
                    if (result.code() > 0) {
                        getFragmentManager().beginTransaction().add(R.id.tab_one_frame_two, PicTextFragment.newInstance("家长课堂",66, result.getAlbum())).commit();
                    }
                    setContentShown(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 67:
                try {
                    if (result.code() > 0) {
                        getFragmentManager().beginTransaction().add(R.id.tab_one_frame_three, PicTextFragment.newInstance("儿童歌曲",67, result.getAlbum())).commit();
                    }
                    setContentShown(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 68:
                try {
                    if (result.code() > 0) {
                        getFragmentManager().beginTransaction().add(R.id.tab_one_frame_four, PicTextFragment.newInstance("音乐欣赏",68, result.getAlbum())).commit();
                    }
                    setContentShown(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 69:
                try {
                    if (result.code() > 0) {
                        getFragmentManager().beginTransaction().add(R.id.tab_one_frame_five, PicTextFragment.newInstance("儿童故事",69, result.getAlbum())).commit();
                    }
                    setContentShown(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 70:
                try {
                    if (result.code() > 0) {
                        getFragmentManager().beginTransaction().add(R.id.tab_one_frame_six, PicTextFragment.newInstance("国学启蒙",70, result.getAlbum())).commit();
                    }
                    setContentShown(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 71:
                try {
                    if (result.code() > 0) {
                        getFragmentManager().beginTransaction().add(R.id.tab_one_frame_seven, PicTextFragment.newInstance("英语精选",71, result.getAlbum())).commit();
                    }
                    setContentShown(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 72:
                try {
                    if (result.code() > 0) {
                        getFragmentManager().beginTransaction().add(R.id.tab_one_frame_eight, PicTextFragment.newInstance("科学百科",72, result.getAlbum())).commit();
                    }
                    setContentShown(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onError(TaskHandle handle, Throwable error) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

