package com.example.elsa.imagesearching.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.core.content.ContextCompat;

public class ViewAnimUtils {
    public interface OnRevealAnimationListener {
        void onRevealHide();

        void onRevealShow();
    }
    //Animate circle display
    public static  void animateRevealShow(final Context context, final View view, int startRadius, final int color, final ViewAnimUtils.OnRevealAnimationListener listener) {
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;

        float finalRadius = (float)Math.hypot((double)view.getWidth(), (double)view.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, (float)startRadius, finalRadius);
        anim.setDuration(300L);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.VISIBLE);
                listener.onRevealShow();
            }

            public void onAnimationStart( Animator animation) {
                super.onAnimationStart(animation);
                view.setBackgroundColor(ContextCompat.getColor(context, color));
            }
        });
        anim.start();
    }

    //Circle coalescing effect
    public static void animateRevealHide(final Context context, final View view, int finalRadius, final int color, final ViewAnimUtils.OnRevealAnimationListener listener) {
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;
        int initialRadius = view.getWidth();
        //The difference with the entrance animation is that the starting and ending radius of the circle are opposite
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, (float)initialRadius, (float)finalRadius);
        anim.setDuration(300L);
        anim.setInterpolator((new AccelerateDecelerateInterpolator()));
        anim.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setBackgroundColor(ContextCompat.getColor(context, color));
            }

            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                listener.onRevealHide();
                view.setVisibility(View.INVISIBLE);
            }
        });
        anim.start();
    }
}
