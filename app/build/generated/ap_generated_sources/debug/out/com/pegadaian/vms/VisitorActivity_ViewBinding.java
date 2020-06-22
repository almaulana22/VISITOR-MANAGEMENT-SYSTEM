// Generated code from Butter Knife. Do not modify!
package com.pegadaian.vms;

import android.view.View;
import android.widget.EditText;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class VisitorActivity_ViewBinding implements Unbinder {
  private VisitorActivity target;

  @UiThread
  public VisitorActivity_ViewBinding(VisitorActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public VisitorActivity_ViewBinding(VisitorActivity target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.rvVisitor, "field 'recyclerView'", RecyclerView.class);
    target.edtSearch = Utils.findRequiredViewAsType(source, R.id.etSearch, "field 'edtSearch'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    VisitorActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.edtSearch = null;
  }
}
