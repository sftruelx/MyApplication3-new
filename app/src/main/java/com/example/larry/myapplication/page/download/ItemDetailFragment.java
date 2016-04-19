package com.example.larry.myapplication.page.download;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.larry.myapplication.R;
import com.example.larry.myapplication.utils.FileUtils;

import java.io.File;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    private int color;
    private ImageView image;
    /**
     * The dummy content this fragment is presenting.
     */
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    String path = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            path = getArguments().getString(ARG_ITEM_ID);
            File file = new File(path);


            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            image = (ImageView)activity.findViewById(R.id.backdrop);

            if (appBarLayout != null) {
                appBarLayout.setTitle(file.getName());

            }
        }
        colorChange();
    }
    private void colorChange() {
        // 用来提取颜色的Bitmap
        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        // Palette的部分
        Palette.Builder from = Palette.from(bitmap);
        Palette palette = from.generate();
        Palette.Swatch vibrant =
                palette.getVibrantSwatch();

//        color =from.generate().getDarkVibrantColor(getResources().getColor(android.R.color.transparent));
        if (vibrant != null) {
            color = colorBurn(vibrant.getRgb());
        } else {
            color = R.color.black;
        }
    }


    private int colorBurn(int RGBValues) {
        int alpha = RGBValues >> 24;
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.rgb(red, green, blue);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.download_detail, container, false);
        File[] files = FileUtils.getMP3(path);
        // Show the dummy content as text in a TextView.
        if (path != null) {
//            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.details);
            RecyclerView rc = (RecyclerView) rootView.findViewById(R.id.item_list);
            setupRecyclerView(rc, files);
        }

        return rootView;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, File[] files) {

        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(files));
    }

    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final File[] mValues;

        public SimpleItemRecyclerViewAdapter(File[] items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.download_list_songs, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mImage.setColorFilter(color);
//            holder.mFavor.setColorFilter(color);
            holder.mDownload.setColorFilter(color);
            holder.mItem = mValues[position];
/*
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

            holder.mImage.setImageBitmap(bitmap);*/
            holder.mContentView.setText(mValues[position].getName());

          /*  holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Context context = v.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.getAbsoluteFile());

                    context.startActivity(intent);
                }
        });*/
    }

    @Override
    public int getItemCount() {
        return mValues.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImage;
        public final ImageView mDownload;
        public final TextView mContentView;
        public File mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImage = (ImageView) view.findViewById(R.id.item_image);
            mDownload = (ImageView) view.findViewById(R.id.download);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}

}
