package com.example.larry.myapplication.page.download;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.larry.myapplication.PlaybackControlsFragment;
import com.example.larry.myapplication.R;
import com.example.larry.myapplication.entity.Album;
import com.example.larry.myapplication.entity.Artist;
import com.example.larry.myapplication.media.ConstMsg;
import com.example.larry.myapplication.utils.Mp3FileFilter;
import com.example.larry.myapplication.utils.MyActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**我的下载播放页面
 * An activity representing a single Item detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 */
public class ItemDetailActivity extends MyActivity {
    ImageView image;
    private int color;
    Bitmap bitmap;
    ArrayList<Artist> list = new ArrayList<Artist>();
    private CollapsingToolbarLayout toolbar;
    private TextView txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_detail);
        File songDir =  ((File)getIntent().getExtras().get(ItemDetailFragment.ARG_ITEM_ID));
        File[] fileList = songDir.listFiles(new Mp3FileFilter());
        if(fileList != null){
            for(File f: fileList){
                Artist artist = new Artist();
                artist.setArtistName(f.getName());
                artist.setArtistPath(f.getAbsolutePath());
                artist.setLocal(1);
                list.add(artist);
            }
        }
        byte[] bis=this.getIntent().getByteArrayExtra("bitmap");
        bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);

        ImageView playImage = (ImageView) findViewById(R.id.play_image);
        image = (ImageView) findViewById(R.id.backdrop);
        txt = (TextView)findViewById(R.id.toolar_text);
        toolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        playImage.setImageBitmap(bitmap);
        image.setImageBitmap(bitmap);
        Toolbar tool = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(tool);
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID,
                    ((File)getIntent().getExtras().get(ItemDetailFragment.ARG_ITEM_ID)).getAbsolutePath());
            arguments.putByteArray(ItemDetailFragment.ARG_BYTE,bis);
            ItemDetailFragment fragment = ItemDetailFragment.newInstance(bis);
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame, fragment)
                    .commit();
        }
        ImageView mButton = (ImageView) findViewById(R.id.play_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                T.showShort(getContext(),"==========播放专辑中的所有文件===========");
/*                Intent intent = new Intent(ConstMsg.MUSICCLIENT_ACTION);
                intent.putExtra(ConstMsg.SONG_STATE, ConstMsg.STATE_PLAYING);

                intent.putParcelableArrayListExtra(ConstMsg.ARISTLIST, list);
                intent.putExtra(ConstMsg.ALBUM, album);
                intent.putExtra(ConstMsg.SONG_COLOR, color);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] bitmapByte = baos.toByteArray();
                intent.putExtra(ConstMsg.SONG_ICON, bitmapByte);
                sendBroadcast(intent);*/
            }
        });
        mControlsFragment = (PlaybackControlsFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_playback_controls);
        if (mControlsFragment == null) {
            throw new IllegalStateException("Mising fragment with id 'controls'. Cannot continue.");
        }
        colorChange();
        applyBlur();
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
//        color = vibrant.getPopulation(); //白色
//        color = vibrant.getBodyTextColor();//白色
//        color = vibrant.getTitleTextColor();
        toolbar.setContentScrimColor(colorBurn(color));

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


    private void applyBlur() {
        image.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                image.getViewTreeObserver().removeOnPreDrawListener(this);
                image.buildDrawingCache();
                Bitmap bmp = image.getDrawingCache();
                blur(bmp, txt);
                return true;
            }
        });
    }

    private void blur(Bitmap bkg, View view) {

        float radius = 25;
        Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth()),
                (int) (view.getMeasuredHeight()), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft(), -view.getTop());
        canvas.drawBitmap(bkg, 0, 0, null);

        RenderScript rs = RenderScript.create(this);
        Allocation overlayAlloc = Allocation.createFromBitmap(
                rs, overlay);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(
                rs, overlayAlloc.getElement());
        blur.setInput(overlayAlloc);
        blur.setRadius(radius);
        blur.forEach(overlayAlloc);
        overlayAlloc.copyTo(overlay);
        view.setBackground(new BitmapDrawable(
                getResources(), overlay));
        rs.destroy();
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
}
