package b135.rowbot_p2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
// this class is not used in this version of the prototype

public class StatisticsImAdapterDist extends PagerAdapter {
    //used to later pass context to the constructor
    private Context statDistContext;
    //array holding the images of the ViewPager (swipeView)
    private int[] statDistImageIds = new int[]
            {R.drawable.weekdistcov_ph, R.drawable.monthdistcov_ph};

    //constructor
    StatisticsImAdapterDist(Context context) {
        statDistContext = context;
    }

    @Override
    public int getCount() {
        return statDistImageIds.length;
    }

    @Override
    //this method gets passed the object that we create in the method below and it functions as a key
    //to let the program know which view belongs to which item
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        //this makes it so that the boolean that is returned is set to true
        //since it just checks if the imageView created below belongs to this object identifier
        return view == object;
    }

    @NonNull
    @Override
    //here we crate an imageView and then adds it to a container which is then returned as an object
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(statDistContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(statDistImageIds[position]);
        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    //this method removes the image when the item is destroyed
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //to remove the view we pass our object into the removeView method and then cast it to an ImageView
        container.removeView((ImageView) object);
    }

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
