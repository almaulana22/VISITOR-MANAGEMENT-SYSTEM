// Generated code from Butter Knife. Do not modify!
package com.pegadaian.vms;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(MainActivity target, View source) {
    this.target = target;

    target.txtWelcome = Utils.findRequiredViewAsType(source, R.id.tvWelcome, "field 'txtWelcome'", TextView.class);
    target.cardTambah = Utils.findRequiredViewAsType(source, R.id.cvTambah, "field 'cardTambah'", CardView.class);
    target.cardCheckin = Utils.findRequiredViewAsType(source, R.id.cvCheckin, "field 'cardCheckin'", CardView.class);
    target.cardCheckout = Utils.findRequiredViewAsType(source, R.id.cvCheckout, "field 'cardCheckout'", CardView.class);
    target.fabVis = Utils.findRequiredViewAsType(source, R.id.fabVisitor, "field 'fabVis'", FloatingActionButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtWelcome = null;
    target.cardTambah = null;
    target.cardCheckin = null;
    target.cardCheckout = null;
    target.fabVis = null;
  }
}
