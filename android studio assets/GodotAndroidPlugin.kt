package com.example.mylibrary;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.Looper;
import android.view.InputDevice;
import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;
import org.godotengine.godot.plugin.UsedByGodot;

public class GodotAndroidPlugin extends GodotPlugin {

    public GodotAndroidPlugin(Godot godot) {
        super(godot);
        registerKeyboardCallback();
    }

    @Override
    public String getPluginName() {
        return "AndroidGetKeys";
    }

    private long stateUpdateDelay = 1500;
    private boolean preState = false;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable emitStateRunnable = this::emitCurrentState;

    @Override
    public MutableSet<SignalInfo> getPluginSignals() {
        MutableSet<SignalInfo> signals = new MutableSet<>();
        signals.add(new SignalInfo("hasKeyboard", String.class));
        return signals;
    }

    private void registerKeyboardCallback() {
        InputManager inputManager = (InputManager) getActivity().getSystemService(Context.INPUT_SERVICE);
        InputManager.InputDeviceListener listener = new InputManager.InputDeviceListener() {
            @Override
            public void onInputDeviceAdded(int deviceId) {
                handler.removeCallbacks(emitStateRunnable);
                handler.postDelayed(emitStateRunnable, stateUpdateDelay);
            }

            @Override
            public void onInputDeviceRemoved(int deviceId) {
                handler.removeCallbacks(emitStateRunnable);
                handler.postDelayed(emitStateRunnable, stateUpdateDelay);
            }

            @Override
            public void onInputDeviceChanged(int deviceId) {
                handler.removeCallbacks(emitStateRunnable);
                handler.postDelayed(emitStateRunnable, stateUpdateDelay);
            }
        };
        inputManager.registerInputDeviceListener(listener, handler);
    }

    private void emitCurrentState() {
        boolean keyboardConnectionState = isKeyboardConnected();

        if (preState == keyboardConnectionState) {
            return;
        }

        preState = keyboardConnectionState;

        emitSignal("hasKeyboard", String.valueOf(keyboardConnectionState));
    }

    @UsedByGodot
    private boolean isKeyboardConnected() {
        InputManager inputManager = (InputManager) getActivity().getSystemService(Context.INPUT_SERVICE);
        int[] deviceIds = inputManager.getInputDeviceIds();

        for (int deviceId : deviceIds) {
            InputDevice device = inputManager.getInputDevice(deviceId);
            if (device != null && (device.getSources() & InputDevice.SOURCE_KEYBOARD) == InputDevice.SOURCE_KEYBOARD) {
                return true;
            }
        }

        return false;
    }
}
