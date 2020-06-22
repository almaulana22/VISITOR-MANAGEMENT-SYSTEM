// Generated code from Butter Knife. Do not modify!
package com.pegadaian.vms;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.jaredrummler.materialspinner.MaterialSpinner;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddVisitorActivity_ViewBinding implements Unbinder {
  private AddVisitorActivity target;

  @UiThread
  public AddVisitorActivity_ViewBinding(AddVisitorActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AddVisitorActivity_ViewBinding(AddVisitorActivity target, View source) {
    this.target = target;

    target.visitorImage = Utils.findRequiredViewAsType(source, R.id.imgVisitorView, "field 'visitorImage'", ImageView.class);
    target.qrImage = Utils.findRequiredViewAsType(source, R.id.imgQr, "field 'qrImage'", ImageView.class);
    target.etNama = Utils.findRequiredViewAsType(source, R.id.etNama, "field 'etNama'", EditText.class);
    target.etPerusahaan = Utils.findRequiredViewAsType(source, R.id.etPerusahaan, "field 'etPerusahaan'", EditText.class);
    target.etTelp = Utils.findRequiredViewAsType(source, R.id.etTelp, "field 'etTelp'", EditText.class);
    target.etEmail = Utils.findRequiredViewAsType(source, R.id.etEmail, "field 'etEmail'", EditText.class);
    target.spTujuan = Utils.findRequiredViewAsType(source, R.id.spTujuan, "field 'spTujuan'", MaterialSpinner.class);
    target.spHost = Utils.findRequiredViewAsType(source, R.id.spHost, "field 'spHost'", MaterialSpinner.class);
    target.txtAddCheck = Utils.findRequiredViewAsType(source, R.id.tvAddCheck, "field 'txtAddCheck'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddVisitorActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.visitorImage = null;
    target.qrImage = null;
    target.etNama = null;
    target.etPerusahaan = null;
    target.etTelp = null;
    target.etEmail = null;
    target.spTujuan = null;
    target.spHost = null;
    target.txtAddCheck = null;
  }
}
