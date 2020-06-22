// Generated code from Butter Knife. Do not modify!
package com.pegadaian.vms;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DetailVisitorActivity_ViewBinding implements Unbinder {
  private DetailVisitorActivity target;

  @UiThread
  public DetailVisitorActivity_ViewBinding(DetailVisitorActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DetailVisitorActivity_ViewBinding(DetailVisitorActivity target, View source) {
    this.target = target;

    target.txtNama = Utils.findRequiredViewAsType(source, R.id.tvDetailNama, "field 'txtNama'", TextView.class);
    target.txtPerusahaan = Utils.findRequiredViewAsType(source, R.id.tvDetailPerusahaan, "field 'txtPerusahaan'", TextView.class);
    target.txtEmail = Utils.findRequiredViewAsType(source, R.id.tvDetailEmail, "field 'txtEmail'", TextView.class);
    target.txtTelp = Utils.findRequiredViewAsType(source, R.id.tvDetailTelp, "field 'txtTelp'", TextView.class);
    target.txtCheckin = Utils.findRequiredViewAsType(source, R.id.tvDetailCheckin, "field 'txtCheckin'", TextView.class);
    target.txtCheckout = Utils.findRequiredViewAsType(source, R.id.tvDetailCheckout, "field 'txtCheckout'", TextView.class);
    target.txtPrintNama = Utils.findRequiredViewAsType(source, R.id.tvPrint, "field 'txtPrintNama'", TextView.class);
    target.imgQr = Utils.findRequiredViewAsType(source, R.id.imgDetailQr, "field 'imgQr'", ImageView.class);
    target.imgVisitor = Utils.findRequiredViewAsType(source, R.id.imgDetailVisitor, "field 'imgVisitor'", ImageView.class);
    target.imgPrintQr = Utils.findRequiredViewAsType(source, R.id.imgPrint, "field 'imgPrintQr'", ImageView.class);
    target.lnPrintVis = Utils.findRequiredViewAsType(source, R.id.lnPrint, "field 'lnPrintVis'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DetailVisitorActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtNama = null;
    target.txtPerusahaan = null;
    target.txtEmail = null;
    target.txtTelp = null;
    target.txtCheckin = null;
    target.txtCheckout = null;
    target.txtPrintNama = null;
    target.imgQr = null;
    target.imgVisitor = null;
    target.imgPrintQr = null;
    target.lnPrintVis = null;
  }
}
