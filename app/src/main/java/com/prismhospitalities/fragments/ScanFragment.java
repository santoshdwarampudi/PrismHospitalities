package com.prismhospitalities.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.Result;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;
import com.nandroidex.upipayments.listener.PaymentStatusListener;
import com.nandroidex.upipayments.models.TransactionDetails;
import com.nandroidex.upipayments.utils.UPIPayment;
import com.prismhospitalities.R;
import com.prismhospitalities.baseui.BaseFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;


public class ScanFragment extends BaseFragment implements ZXingScannerView.ResultHandler,
        PaymentStatusListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private static final int REQUEST_CAMERA = 1;
    @BindView(R.id.zxing_barcode_scanner)
    ZXingScannerView zXingScannerView;
    @BindView(R.id.ib_flashOn)
    ImageView ibFlashOn;
    @BindView(R.id.ib_flashOff)
    ImageView ibFlashOff;
    private UPIPayment upiPayment;


    public ScanFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ScanFragment newInstance(String param1, String param2) {
        ScanFragment fragment = new ScanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_scan;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
            } else {
                requestPermission();
            }
        }
        ibFlashOff.setOnClickListener(this);
        ibFlashOn.setOnClickListener(this);
        return view;
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(getActivity(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{CAMERA}, REQUEST_CAMERA);

    }


    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if (zXingScannerView == null) {
                    zXingScannerView = new ZXingScannerView(getActivity());
                }
                zXingScannerView.setResultHandler(this);
                zXingScannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (zXingScannerView != null)
            zXingScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        // showScanResultDialogue(result);
        startUpiPayment();
    }

    private void startUpiPayment() {
        long millis = System.currentTimeMillis();
        upiPayment = new UPIPayment.Builder()
                .with(getActivity())
                .setPayeeVpa(getString(R.string.vpa))
                .setPayeeName(getString(R.string.payee))
                .setTransactionId(Long.toString(millis))
                .setTransactionRefId(Long.toString(millis))
                .setDescription(getString(R.string.transaction_description))
                .setAmount(getString(R.string.amount))
                .build();

        upiPayment.setPaymentStatusListener(this);

        if (upiPayment.isDefaultAppExist()) {
            onAppNotFound();
            return;
        }

        upiPayment.startPayment();
    }

    private void showScanResultDialogue(Result result) {
        final String scanResult = result.getText();
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Scan Result");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onResume();
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /*Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(scanResult));
                startActivity(intent);*/
                onResume();
            }
        });
        builder.setMessage(scanResult);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public void onRequestPermissionResult(int requestCode, String permission[], int grantResults[]) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {
                    boolean camearAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (camearAccepted) {
                    } else {
                        showToast("Permission Denied");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                displayAlertMessage("You need to allow access for both permission",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                                }

                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("Ok", listener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onAppNotFound() {
        showToast("App Not Found");
    }

    @Override
    public void onTransactionCancelled() {
        showToast("Cancelled");
    }

    @Override
    public void onTransactionCompleted(@Nullable TransactionDetails transactionDetails) {
        String status = null;
        String approvalRefNo = null;
        if (transactionDetails != null) {
            status = transactionDetails.getStatus();
            approvalRefNo = transactionDetails.getApprovalRefNo();
        }
        boolean success = false;
        if (status != null) {
            success = status.equalsIgnoreCase("success") || status.equalsIgnoreCase("submitted");
        }
        int dialogType = success ? DialogTypes.TYPE_SUCCESS : DialogTypes.TYPE_ERROR;
        String title = success ? "Good job!" : "Oops!";
        String description = success ? ("UPI ID :" + approvalRefNo) : "Transaction Failed/Cancelled";
        int buttonColor = success ? Color.parseColor("#00C885") : Color.parseColor("#FB2C56");
        LottieAlertDialog alertDialog = new LottieAlertDialog.Builder(getActivity(), dialogType)
                .setTitle(title)
                .setDescription(description)
                .setNoneText("Okay")
                .setNoneTextColor(Color.WHITE)
                .setNoneButtonColor(buttonColor)
                .setNoneListener(new ClickListener() {
                    @Override
                    public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                        lottieAlertDialog.dismiss();
                    }
                })
                .build();
        alertDialog.setCancelable(false);
        alertDialog.show();
        upiPayment.detachListener();
    }

    @Override
    public void onTransactionFailed() {
        showToast("Failed");
    }

    @Override
    public void onTransactionSubmitted() {
        showToast("Pending | Submitted");
    }

    @Override
    public void onTransactionSuccess() {
        showToast("Success");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_flashOff:
                zXingScannerView.setFlash(false);
                ibFlashOn.setVisibility(View.VISIBLE);
                ibFlashOff.setVisibility(View.GONE);
                break;
            case R.id.ib_flashOn:
                zXingScannerView.setFlash(true);
                ibFlashOn.setVisibility(View.GONE);
                ibFlashOff.setVisibility(View.VISIBLE);
                break;
        }
    }
}
