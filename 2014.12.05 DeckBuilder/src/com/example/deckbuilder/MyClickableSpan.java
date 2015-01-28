package com.example.deckbuilder;

import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyClickableSpan extends ClickableSpan {
	   private final String mText;
	   public MyClickableSpan(final String text) {
	      mText = text;
	   }
	   @Override
	   public void onClick(final View widget) {
		   SearchActivity.show(mText.split("\n")[0]);
//		   Toast.makeText(widget.getContext(), mText.toString(), Toast.LENGTH_SHORT).show();
	   }
	}