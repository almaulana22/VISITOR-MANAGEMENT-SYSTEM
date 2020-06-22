// Generated code from Butter Knife. Do not modify!
package com.pegadaian.vms;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CheckinActivity_ViewBinding implements Unbinder {
  private CheckinActivity target;

  @UiThread
  public CheckinActivity_ViewBinding(CheckinActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CheckinActivity_ViewBinding(CheckinActivity target, View source) {
    this.target = target;

    target.qrCodeReaderView = Utils.findRequiredViewAsType(source, R.id.qrReader, "field 'qrCodeReaderView'", QRCodeReaderView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CheckinActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.qrCodeReaderView = null;
  }
}
