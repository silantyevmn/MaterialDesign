package silantyevmn.ru.materialdesign.view.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by silan on 13.09.2018.
 */

public class FabHideOnScrollBehavior extends FloatingActionButton.Behavior {
    public FabHideOnScrollBehavior() {
    }

    public FabHideOnScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child,
                               @NonNull View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed, @ViewCompat.NestedScrollType int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
//        child -> Floating Action Button
//        if (child.getVisibility() == View.VISIBLE && dyConsumed > 0) {
//            child.hide(new FloatingActionButton.OnVisibilityChangedListener() {
//                @Override
//                public void onHidden(FloatingActionButton fab) {
//                    super.onHidden(fab);
//                    //
//                    fab.setVisibility(View.INVISIBLE);
//                }
//            });
//        } else if (child.getVisibility() == View.INVISIBLE && dyConsumed < 0) {
//            child.show();
//        }

        if (dyConsumed > 0) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            int fab_bottomMargin = layoutParams.bottomMargin;
            child.animate().translationY(child.getHeight() + fab_bottomMargin).setInterpolator(new LinearInterpolator()).start();
        } else if (dyConsumed < 0) {
            child.animate().translationY(0).setInterpolator(new LinearInterpolator()).start();
        }

    }

    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target,
                                       @ViewCompat.ScrollAxis int axes, @ViewCompat.NestedScrollType int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }
}
