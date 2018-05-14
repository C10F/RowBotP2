package b135.rowbot_p2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class StatisticsImAdapterTS extends FragmentPagerAdapter {
    /*//used to later pass context to the constructor
    private Context statTimeSpentContext;
    //array holding the images of the ViewPager (swipeView)
    private int[] statTimeSpentImageIds = new int[]
            {R.drawable.weektimespent_ph, R.drawable.monthtimespent_ph};*/

    //Constructor
    public StatisticsImAdapterTS(FragmentManager fm) {
        super(fm);
    }

    @Override
    //this returns the amount of pages in the adapter.
    public int getCount() {
        return 2;
    }

    /*@Override
    //this method gets passed the object that we create in the method below and it functions as a key
    //to let the program know which view belongs to which item
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        //this makes it so that the boolean that is returned is set to true
        //since it just checks if the imageView created below belongs to this object identifier
        return view == object;
    }*/

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentsStatisticsTimeWeek();
            case 1:
                return new FragmentsStatisticsTimeMonth();
            default:
                break;
        }
        return null;
    }

    /*@NonNull
    @Override
    //here we crate an imageView and then adds it to a container which is then returned as an object
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(statTimeSpentContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(statTimeSpentImageIds[position]);
        container.addView(imageView, 0);
        return imageView;
    }*/

    /*@Override
    //this method removes the image when the item is destroyed
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //to remove the view we pass our object into the removeView method and then cast it to an ImageView
        container.removeView((ImageView) object);
    }*/

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "Week";
        }
        else if (position == 1) {
            title = "Month";
        }
        return title;
    }
}
