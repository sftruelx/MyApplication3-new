package com.example.larry.myapplication.page;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.larry.myapplication.MainActivity;
import com.example.larry.myapplication.R;
import com.example.larry.myapplication.songList.SongDetailActivity;
import com.example.larry.myapplication.songList.SongListActivity;
import com.example.larry.myapplication.utils.LogHelper;

import java.io.ByteArrayOutputStream;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClassifyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClassifyFragment#newInstance} factory method to
 * create an instance of this fragment.
 * 分类页面，现在只包含5个选项
 */
public class ClassifyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ClassifyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClassifyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClassifyFragment newInstance(String param1, String param2) {
        ClassifyFragment fragment = new ClassifyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_classify, container, false);
/*        View cv = rootView.findViewById(R.id.no1);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出列表窗口
                Intent intent = new Intent(view.getContext(), ShowPanelActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
            }
        });*/
        View one = rootView.findViewById(R.id.no1);
        one.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //弹出列表窗口
                Intent intent = new Intent(view.getContext(), ShowListActivity.class);
                intent.putExtra("classifyId",65);
                intent.putExtra("title","必读经典");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
        View two = rootView.findViewById(R.id.no2);
        two.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //弹出列表窗口
                Intent intent = new Intent(view.getContext(), ShowListActivity.class);
                intent.putExtra("classifyId",66);
                intent.putExtra("title","家长课堂");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
        View three = rootView.findViewById(R.id.no3);
        three.setOnClickListener(new View.OnClickListener(){
                                   @Override
                                   public void onClick(View view) {
                                       //弹出列表窗口
                                       Intent intent = new Intent(view.getContext(), ShowListActivity.class);
                                       intent.putExtra("classifyId",67);
                                       intent.putExtra("title","儿童歌曲");
                                       startActivity(intent);
                                       getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                                   }
        });
        View four = rootView.findViewById(R.id.no4);
        four.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //弹出列表窗口
                Intent intent = new Intent(view.getContext(), ShowListActivity.class);
                intent.putExtra("classifyId",68);
                intent.putExtra("title","音乐欣赏");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
        View five = rootView.findViewById(R.id.no5);
        five.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //弹出列表窗口
                Intent intent = new Intent(view.getContext(), ShowListActivity.class);
                intent.putExtra("classifyId",69);
                intent.putExtra("title","儿童故事");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
        View six = rootView.findViewById(R.id.no6);
        six.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //弹出列表窗口
                Intent intent = new Intent(view.getContext(), ShowListActivity.class);
                intent.putExtra("classifyId",70);
                intent.putExtra("title","国学启蒙");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
        View seven = rootView.findViewById(R.id.no7);
        seven.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //弹出列表窗口
                Intent intent = new Intent(view.getContext(), ShowListActivity.class);
                intent.putExtra("classifyId",71);
                intent.putExtra("title","英语精选");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
        View eight = rootView.findViewById(R.id.no8);
        eight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //弹出列表窗口
                Intent intent = new Intent(view.getContext(), ShowListActivity.class);
                intent.putExtra("classifyId",72);
                intent.putExtra("title","科学百科");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

/*    @Override
    public void onAttach(Context context) {
//        super.onAttach(context);
       *//* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*//*
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
