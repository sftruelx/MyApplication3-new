package com.example.larry.myapplication.page.download;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.larry.myapplication.R;
import com.example.larry.myapplication.songList.SongDetailActivity;
import com.example.larry.myapplication.utils.AppUrl;
import com.example.larry.myapplication.utils.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
    //获取文件列表
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        File[] files = FileUtils.getFiles(FileUtils.SDPath+ AppUrl.DIR);
        if(files != null) {
            recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(files));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final File[] mValues;

        public SimpleItemRecyclerViewAdapter(File[] items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.download_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues[position];

            File[] images = holder.mItem.listFiles(new FileFilter() {

                @Override
                public boolean accept(File pathname) {
                    String filename = pathname.getPath();
                    if (pathname.isDirectory())
                        return true;
                    if(filename.endsWith(".jpg"))
                        return true;
                    else
                        return false;
                }
            });

            Bitmap bitmap = BitmapFactory.decodeFile(images[0].getAbsolutePath());

            holder.mImage.setImageBitmap(bitmap);
            holder.mContentView.setText(mValues[position].getName());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(context, SongDetailActivity.class);
//                    intent.putExtra(SongDetailActivity.ARG_ITEM_ID, album);
//                    Bitmap bmp = ((BitmapDrawable) mListImg.getDrawable()).getBitmap();
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
//                    byte[] bitmapByte = baos.toByteArray();
//                    intent.putExtra("bitmap", bitmapByte);
//                    context.startActivity(intent);
//                    getActivity().overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);


                        Context context = v.getContext();
                        Intent intent = new Intent(context, ItemDetailActivity.class);
                        intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, holder.mItem);
                        Bitmap bmp = ((BitmapDrawable) holder.mImage.getDrawable()).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] bitmapByte = baos.toByteArray();
                        intent.putExtra("bitmap", bitmapByte);
                        context.startActivity(intent);
                        overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageView mImage;
            public final TextView mContentView;
            public File mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImage = (ImageView) view.findViewById(R.id.image);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
