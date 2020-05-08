package com.example.practice_0507_recyclerview_databinding;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel extends ViewModel implements Observable {
	private transient PropertyChangeRegistry mCallbacks;

	@Override
	public void addOnPropertyChangedCallback(
		@NonNull Observable.OnPropertyChangedCallback callback) {
		synchronized (this) {
			if (mCallbacks == null) {
				mCallbacks = new PropertyChangeRegistry();
			}
		}
		mCallbacks.add(callback);
	}

	@Override
	public void removeOnPropertyChangedCallback(@NonNull
		Observable.OnPropertyChangedCallback callback) {
		synchronized (this) {
			if (mCallbacks == null) {
				return;
			}
		}
		mCallbacks.remove(callback);
	}

	/**
	 * Notifies listeners that all properties of this instance have changed.
	 */
	public void notifyChange() {
		synchronized (this) {
			if (mCallbacks == null) {
				return;
			}
		}
		mCallbacks.notifyCallbacks(this, 0, null);
	}

	/**
	 * Notifies listeners that a specific property has changed. The getter for the property
	 * that changes should be marked with {@link Bindable} to generate a field in
	 * <code>BR</code> to be used as <code>fieldId</code>.
	 *
	 * @param fieldId The generated BR id for the Bindable field.
	 */
	public void notifyPropertyChanged(int fieldId) {
		synchronized (this) {
			if (mCallbacks == null) {
				return;
			}
		}
		mCallbacks.notifyCallbacks(this, fieldId, null);
	}
}
