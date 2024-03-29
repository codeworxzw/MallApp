package com.mallapp.Fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

import com.ViewPager.Adapter.OffersNewsPagerAdapter;
import com.astuetz.PagerSlidingTabStrip;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.Constants.MainMenuConstants;
import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Controllers.FavouriteCentersFiltration;
import com.mallapp.Controllers.OffersNewsFiltration;
import com.mallapp.Model.FavouriteCenters;
import com.mallapp.Model.FavouriteCentersModel;
import com.mallapp.SharedPreferences.DataHandler;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.View.DashboardTabFragmentActivity;
import com.mallapp.View.R;
import com.mallapp.cache.CentersCacheManager;
import com.mallapp.layouts.SegmentedRadioGroup;
import com.mallapp.listeners.MallDataListener;
import com.mallapp.listeners.NearbyListener;
import com.mallapp.utils.AppUtils;
import com.mallapp.utils.GlobelOffersNews;
import com.mallapp.utils.Log;
import com.mallapp.utils.RegistrationController;
import com.mallapp.utils.Utils;
import com.mallapp.utils.VolleyNetworkUtil;
import com.squareup.picasso.Picasso;

public class OffersTabFragment extends Fragment
        implements OnCheckedChangeListener, NearbyListener {

    String TAG = getClass().getCanonicalName();

    static Context context;
    private ViewPager pager;
    SegmentedRadioGroup segmentText;
    private PagerSlidingTabStrip tabs;
    public static ImageView center_logo;
    private ImageButton navigation_button;
    RelativeLayout headerLayoutColor;
    private OffersNewsPagerAdapter adapter;
    public static String selected_center_name;
    public static String selected_center_logo;

    private ArrayList<String> TITLES = new ArrayList<String>();
    String audienceFilter = Offers_News_Constants.AUDIENCE_FILTER_ALL;
    VolleyNetworkUtil volleyNetworkUtil;
    RegistrationController registrationController;
    public static Handler uihandler;
    int tabPos = 0;
    public static int pos = 0;
    RadioButton radio_all, radio_offer, radio_news;
    String urls;
    public static boolean onResume = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, " on create OffersTabFragment ");
        context = getActivity().getApplicationContext();
//        TITLES = FavouriteCentersFiltration.getFavTITLES(context);
//        volleyNetworkUtil = new VolleyNetworkUtil(context);
        uihandler = MainMenuConstants.uiHandler;
        registrationController = new RegistrationController(context);
        urls = ApiConstants.GET_USER_MALL_URL_KEY + SharedPreferenceUserProfile.getUserId(context);
        registrationController.GetSubscribedMallList(urls, this);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_parent_tab_offer, container, false);

        navigation_button = (ImageButton) view.findViewById(R.id.navigation_drawer);
        center_logo = (ImageView) view.findViewById(R.id.center_logo);
        radio_offer = (RadioButton) view.findViewById(R.id.button_two);
        radio_news = (RadioButton) view.findViewById(R.id.button_three);
        headerLayoutColor = (RelativeLayout) view.findViewById(R.id.headerColor);
        headerLayoutColor.setBackgroundColor(getResources().getColor(R.color.purple));
        center_logo.setImageResource(R.drawable.logo);
        segmentText = (SegmentedRadioGroup) view.findViewById(R.id.segment_text);
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        pager = (ViewPager) view.findViewById(R.id.pager);

        segmentText.setOnCheckedChangeListener(this);

        tabs.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
                //Log.e(" offers tab fragment ", "onPageScrollStateChanged");
            }

            @Override
            public void onPageScrolled(int position, float arg1, int arg2) {
                //Log.e(" offers tab fragment ", "onPageScrolled" + position);
            }

            @Override
            public void onPageSelected(int position) {
                //Log.e(" offers tab fragment ", "onPageSelected" + position+" centers fav size = "+ TITLES.size());
                setCenterLogo(position);
                tabPos = position;
                pos = position;
//                callInOnResume();

            }
        });

        navigation_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardTabFragmentActivity.uiHandler.sendEmptyMessage(1);

            }
        });
        if (onResume) {
            if (Utils.isInternetAvailable(context))
                callInOnResume();
            else
                AppUtils.internetDialog(getActivity(), getResources().getString(R.string.no_internet), getResources().getString(R.string.network_error));
        }
        //	return inflater.inflate(R.layout.fragment_parent_tab_offer, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.e(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    protected void setCenterLogo(int position) {

        String selectedCenter = TITLES.get(position).trim();
        setSelected_center_name(selectedCenter);


        if (position == 0) {
            headerLayoutColor.setBackgroundColor(getResources().getColor(R.color.purple));
            center_logo.setImageResource(R.drawable.logo);
            MainMenuConstants.SELECTED_CENTER_LOGO = null;
            MainMenuConstants.SELECTED_MALL_PLACE_ID = "";

        } else {
            ArrayList<FavouriteCentersModel> TITLES_Centers = GlobelOffersNews.TITLES_centers;
            if (TITLES_Centers == null || TITLES_Centers.size() == 0) {
                try {
                    TITLES_Centers = CentersCacheManager.readSelectedObjectList(context);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (position > 0) {
                FavouriteCentersModel center = TITLES_Centers.get(position - 1);
                String center_logo_name = center.getLogoUrl();
                if (center.getCorporateColor() != null)
                    headerLayoutColor.setBackgroundColor(Color.parseColor(center.getCorporateColor()));
                setCenter_logo(center_logo_name);
                setSelected_center_logo(center_logo_name);
            }
            /*for (FavouriteCentersModel center : TITLES_Centers) {
                if (center.isIsfav() && center.getName().trim().equals(selectedCenter)) {
                    String center_logo_name = center.getLogoUrl();
//                    MainMenuConstants.SELECTED_MALL_PLACE_ID = center.getMallPlaceId();
                    if (center.getCorporateColor() != null)
                        headerLayoutColor.setBackgroundColor(Color.parseColor(center.getCorporateColor()));
                    setCenter_logo(center_logo_name);
                    setSelected_center_logo(center_logo_name);
                }
            }*/
        }
    }

    @Override
    public void onResume() {
//        callInOnResume();
        super.onResume();
        if (ProfileTabFragment.isUpdate || OfferPagerTabFragment.isRefresh) {
            ProfileTabFragment.isUpdate = false;
            OfferPagerTabFragment.isRefresh = false;
            registrationController.GetSubscribedMallList(urls, this);
           /* TITLES = FavouriteCentersFiltration.getFavTITLES(context);
            callInOnResume();*/
        }
    }

    @Override
    public void onDestroyView() {
        Log.e(TAG, "destory view");
        destoryAllInstences();
        onResume = true;
        super.onDestroyView();
    }

    private void destoryAllInstences() {
        GlobelOffersNews.fragment_instences = null;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == segmentText) {
            if (checkedId == R.id.button_one) {
                audienceFilter = Offers_News_Constants.AUDIENCE_FILTER_ALL;
                radio_offer.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.offers_radio), null, null, null);
                radio_news.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.news_radio), null, null, null);
            } else if (checkedId == R.id.button_two) {
                audienceFilter = Offers_News_Constants.AUDIENCE_FILTER_OFFERS;
                radio_offer.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.p_offers_radio), null, null, null);
                radio_news.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.news_radio), null, null, null);
            } else if (checkedId == R.id.button_three) {
                audienceFilter = Offers_News_Constants.AUDIENCE_FILTER_NEWS;
                radio_news.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.p_news_radio), null, null, null);
                radio_offer.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.offers_radio), null, null, null);
            }
            if (Utils.isInternetAvailable(context))
                callInOnResume();
            else
                AppUtils.internetDialog(getActivity(), getResources().getString(R.string.no_internet), getResources().getString(R.string.network_error));
        }
    }

    private void callInOnResume() {
        Log.e(TAG, "On Resme call");
        if (GlobelOffersNews.fragment_instences == null) {
            createPagerIntence();
        } else {
            changeInstanceType();
        }
    }

    private void createPagerIntence() {
        OffersNewsFiltration.readOffersNews(context);
//        List<OfferPagerTabFragment> fragments = getFavouriteFragments();
        adapter = new OffersNewsPagerAdapter(getChildFragmentManager(), context, uiHandler, audienceFilter, TITLES);
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        tabs.setTextColor(Color.parseColor("#663399"));
        tabs.setIndicatorColor(Color.parseColor("#663399"));
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
                getResources().getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        pager.setCurrentItem(tabPos);
//        adapter.notifyDataSetChanged();
    }

    private List<OfferPagerTabFragment> changeInstanceType() {
        List<OfferPagerTabFragment> fragments = GlobelOffersNews.fragment_instences;
        for (int i = 0; i < fragments.size(); i++) {
            OfferPagerTabFragment f = fragments.get(i);
            f.setHeaderFilter(audienceFilter);
            f.changeType_Notification(audienceFilter);
            fragments.set(i, f);
        }
        return fragments;
    }

    private List<OfferPagerTabFragment> getFavouriteFragments() {
        List<OfferPagerTabFragment> instances = new ArrayList<OfferPagerTabFragment>();
//		if(TITLES == null || TITLES.size()==0)
//			TITLES = SharedPreferenceFavourites.getFavouritesList(context);
//		
        for (int i = 0; i < TITLES.size(); i++)
            instances.add(OfferPagerTabFragment.newInstance(i, uiHandler, context, audienceFilter));

//		GlobelOffersNews.TITLES= TITLES;
        GlobelOffersNews.fragment_instences = instances;
        return instances;
    }

    public static ImageView getCenter_logo() {
        return center_logo;
    }

    public static void setCenter_logo(String center_logo) {
        Picasso.with(context).load(center_logo).placeholder(R.drawable.mallapp_placeholder).error(R.drawable.mallapp_placeholder).into(OffersTabFragment.center_logo);
    }

    public static String getSelected_center_name() {
        return MainMenuConstants.SELECTED_CENTER_NAME;
    }

    public static void setSelected_center_name(String selected_center_name) {
        OffersTabFragment.selected_center_name = selected_center_name;
        MainMenuConstants.SELECTED_CENTER_NAME = selected_center_name;
        Log.e("", "selected center name = " + selected_center_name);
    }


    public static String getSelected_center_logo() {
        return selected_center_logo;
    }


    public static void setSelected_center_logo(String selected_center_logo) {
        OffersTabFragment.selected_center_logo = selected_center_logo;
        MainMenuConstants.SELECTED_CENTER_LOGO = selected_center_logo;
    }


    @SuppressLint("HandlerLeak")
    public Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.e(TAG, "Handler works");
                    destoryAllInstences();//Globel_Endorsement.fragment_instences = null;
                    callInOnResume();
                    break;
            }
        }

        ;
    };


    @Override
    public void onMallDataReceived(ArrayList<FavouriteCentersModel> mallList) {
        GlobelOffersNews.TITLES_centers = mallList;
        DataHandler.updatePreferences(GlobelOffersNews.TITLES_centers, "TITLES_centers");
        TITLES = new ArrayList<>();
        TITLES.add(Offers_News_Constants.AUDIENCE_FILTER_ALL);
        for (FavouriteCentersModel favouriteCentersModel : mallList
                ) {
            TITLES.add(favouriteCentersModel.getName());
        }
        GlobelOffersNews.TITLES = TITLES;
        DataHandler.updateStringArrayListPreferences(TITLES, "Titles");
        callInOnResume();
    }

    @Override
    public void onError() {
        TITLES = new ArrayList<>();
        TITLES = DataHandler.getStringArrayListPreference("Titles");
        if (TITLES.size()>0){
            GlobelOffersNews.TITLES_centers = DataHandler.getArrayPreference("TITLES_centers");
            GlobelOffersNews.TITLES = TITLES;
            callInOnResume();
        }
        else{
            TITLES.add(Offers_News_Constants.AUDIENCE_FILTER_ALL);
            callInOnResume();
        }




    }
}