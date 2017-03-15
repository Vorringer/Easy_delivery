package com.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gigamole.infinitecycleviewpager.VerticalInfiniteCycleViewPager;
import com.example.yangj.easy_d1.R;
import com.example.yangj.easy_d1.utils.Utils;

import static com.example.yangj.easy_d1.utils.Utils.setupImage;

/**
 * Created by GIGAMOLE on 7/27/16.
 */
public class HorizontalPagerAdapter extends PagerAdapter {

    private final Utils.LibraryObject[] LIBRARIES = new Utils.LibraryObject[]{
            new Utils.LibraryObject(
                    "https://www.ele.me/shop/553718",
                    "https://s-media-cache-ak0.pinimg.com/564x/8a/b3/4e/8ab34ec6bd0f0584a66e910dae8c6856.jpg"
            ),
            new Utils.LibraryObject(
                    "https://www.ele.me/shop/257384",
                    "https://s-media-cache-ak0.pinimg.com/564x/53/19/24/531924ccfc4cbc897e25669f7c4ecf70.jpg"
            ),
            new Utils.LibraryObject(
                    "https://www.ele.me/shop/308536",
                    "https://s-media-cache-ak0.pinimg.com/564x/aa/bc/54/aabc54f35de203571eb749b4b8a37341.jpg"
            )
    };

    private final Utils.LibraryObject[] TWO_WAY_LIBRARIES = new Utils.LibraryObject[]{
            new Utils.LibraryObject(
                    "https://github.com/DevLight-Mobile-Agency/AnimateFilterCalendarGridLayout",
                    new String[]{
                            "https://lh6.googleusercontent.com/-OMVt5Ow2tsE/VsbvbmJOouI/AAAAAAAACLs/aQoAzX5hGYw/w386-h551-no/afgl.gif",
                            "https://lh4.googleusercontent.com/-dXoUnGjGLHE/VsbvbtehV3I/AAAAAAAACLs/EqYB6AA5fYI/w396-h552-no/acgl.gif"
                    }
            ),
            new Utils.LibraryObject(
                    "https://github.com/DevLight-Mobile-Agency/SlideImageView",
                    new String[]{
                            "https://lh5.googleusercontent.com/-2BiBFz-OW_s/VU_EPv33XMI/AAAAAAAAAcc/nGie_kBoZQ8/w244-h368-no/siv.gif"
                    }
            ),
            new Utils.LibraryObject(
                    "https://github.com/DevLight-Mobile-Agency/MillSpinners",
                    new String[]{
                            "https://lh3.googleusercontent.com/-xmtjZYfrZ3g/VR-7WR2tQ1I/AAAAAAAAAZs/YzLSSYPukYE/s0/multi_arc_spinner.gif",
                            "https://lh5.googleusercontent.com/-PaQXf0zlY5E/VU00AZvffaI/AAAAAAAAAa4/VH36UhvzXl4/w153-h165-no/cws.gif",
                            "https://lh4.googleusercontent.com/-ysG5WmZKG0w/VU00Ao5_MDI/AAAAAAAAAa0/bYf3v90XpcA/w144-h146-no/cs.gif",
                            "https://lh5.googleusercontent.com/-X61M2jmXkTs/VU00AdyLk9I/AAAAAAAAAbI/4BKr42uJ4Vc/w170-h261-no/dls.gif",
                            "https://lh5.googleusercontent.com/-b8PfdN9PoLw/VU00BPNG2cI/AAAAAAAAAbE/AXDZuOMEb5k/w175-h262-no/fs.gif"
                    }
            )
    };

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private boolean mIsTwoWay;

    public HorizontalPagerAdapter(final Context context, final boolean isTwoWay) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mIsTwoWay = isTwoWay;
    }

    @Override
    public int getCount() {
        return mIsTwoWay ? TWO_WAY_LIBRARIES.length : LIBRARIES.length;
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view=null;
        if (mIsTwoWay) {
//            view = mLayoutInflater.inflate(R.layout.two_way_item, container, false);
//
//            final VerticalInfiniteCycleViewPager verticalInfiniteCycleViewPager =
//                    (VerticalInfiniteCycleViewPager) view.findViewById(R.id.vicvp);
//            verticalInfiniteCycleViewPager.setAdapter(
//                    new VerticalPagerAdapter(mContext, TWO_WAY_LIBRARIES[position])
//            );
//            verticalInfiniteCycleViewPager.setCurrentItem(0);
        } else {
            view = mLayoutInflater.inflate(R.layout.item, container, false);
            setupImage(mContext, (ImageView) view.findViewById(R.id.image), LIBRARIES[position]);
        }

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }
}
