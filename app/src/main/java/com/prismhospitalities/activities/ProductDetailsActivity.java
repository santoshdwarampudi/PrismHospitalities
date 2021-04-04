package com.prismhospitalities.activities;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.prismhospitalities.R;
import com.prismhospitalities.adapters.ProductDetailImagesAdapter;
import com.prismhospitalities.baseui.BaseAppCompactActivity;
import com.prismhospitalities.interfaces.IMenuProductView;
import com.prismhospitalities.interfaces.StringConstants;
import com.prismhospitalities.models.responses.AddCartResponse;
import com.prismhospitalities.models.responses.MenuProductsImagesResponse;
import com.prismhospitalities.models.responses.MenuProductsResponse;
import com.prismhospitalities.models.responses.MenuProductsResponseData;
import com.prismhospitalities.presenters.MenuProductPresenter;
import com.prismhospitalities.utils.APIClient;
import com.prismhospitalities.utils.PrefUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class ProductDetailsActivity extends BaseAppCompactActivity implements IMenuProductView,
        ProductDetailImagesAdapter.ItemListener {
    private MenuProductsResponseData menuProductsResponseData;
    private ProductDetailImagesAdapter productDetailImagesAdapter;
    private MenuProductPresenter menuProductPresenter;
    @BindView(R.id.tv_heading)
    TextView tv_heading;
    @BindView(R.id.rv_images)
    RecyclerView rv_images;
    @BindView(R.id.iv_menuImage)
    ImageView iv_menuImage;
    @BindView(R.id.tv_addCart)
    TextView tv_addCart;
    @BindView(R.id.quantity_layout)
    LinearLayout quantity_layout;
    @BindView(R.id.tv_quantity)
    TextView tv_quantity;

    @OnClick(R.id.iv_menu)
    public void onBackClick() {
        finish();
    }

    @OnClick(R.id.tv_addCart)
    void onAddCartClick() {
        tv_addCart.setVisibility(View.GONE);
        quantity_layout.setVisibility(View.VISIBLE);
        if (menuProductPresenter == null)
            menuProductPresenter = new MenuProductPresenter(this, APIClient.getAPIService());
        menuProductPresenter.addOrRemoveFromCart(true, menuProductsResponseData.getId(),
                PrefUtils.getInstance().getUserId() + "", "1", 0);
    }

    @OnClick(R.id.tv_plus)
    void onPlusClick() {
        int quantity = changeQuantity(true);
        menuProductPresenter.addOrRemoveFromCart(true, menuProductsResponseData.getId(),
                PrefUtils.getInstance().getUserId() + "", "1", 0);
    }

    @OnClick(R.id.tv_minus)
    void onMinusClick() {
        if (tv_quantity.getText().toString().equals("1")) {
            tv_addCart.setVisibility(View.VISIBLE);
            quantity_layout.setVisibility(View.GONE);
               /* if (itemListener != null) {
                    itemListener.onAddCartClick(0 + "",
                            menuProductsResponseDataList.get(getAdapterPosition()),
                            getAdapterPosition());
                }*/
        } else {
            int quantity = changeQuantity(false);
            if (quantity == 0) {
                tv_addCart.setVisibility(View.VISIBLE);
                quantity_layout.setVisibility(View.GONE);
            }
              /*  if (itemListener != null) {
                    itemListener.onAddCartClick(quantity + "",
                            menuProductsResponseDataList.get(getAdapterPosition()),
                            getAdapterPosition());
                }*/
        }
    }

    private int changeQuantity(boolean isAdd) {
        int quantity = 0;
        String str_quantity = tv_quantity.getText().toString();
        if (str_quantity != null && !str_quantity.isEmpty()) {
            quantity = Integer.parseInt(str_quantity);
        }
        if (quantity != 0) {
            if (isAdd) {
                quantity++;
            } else {
                quantity--;
            }
        }
        tv_quantity.setText(quantity + "");
        return quantity;
    }


    @Override
    public int getActivityLayout() {
        return R.layout.activity_product_details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            initRecyclerView();
            menuProductsResponseData = (MenuProductsResponseData) bundle.getSerializable(StringConstants.RESPONSE);
            if (menuProductsResponseData != null) {
                tv_heading.setText(menuProductsResponseData.getTitle().toUpperCase());
                if (menuProductsResponseData.getPrimages() != null && menuProductsResponseData.getPrimages().size() > 0)
                    productDetailImagesAdapter.setData(menuProductsResponseData.getPrimages(), menuProductsResponseData.getImagepath());
                rv_images.scrollToPosition(0);
                String image = menuProductsResponseData.getImagepath() + "/" + menuProductsResponseData.getMenuDisplayImage();
                Glide.with(ProductDetailsActivity.this).load(image)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_horizontalbanner).error(R.drawable.ic_horizontalbanner)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(iv_menuImage);
            }
        }
    }

    private void initRecyclerView() {
        productDetailImagesAdapter = new ProductDetailImagesAdapter(ProductDetailsActivity.this, null, null, this::onItemClick);
        rv_images.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_images.setItemAnimator(new DefaultItemAnimator());
        rv_images.setAdapter(productDetailImagesAdapter);
        rv_images.setNestedScrollingEnabled(false);
    }

    @Override
    public void onItemClick(int position, MenuProductsImagesResponse menuProductsImagesResponse) {

    }

    @Override
    public void getMenuProductsSuccess(MenuProductsResponse menuProductsResponse) {

    }

    @Override
    public void getMenuProductsFailed() {

    }

    @Override
    public void addToCartSuccess(AddCartResponse addCartResponse, int position) {

    }

    @Override
    public void addToCartFailed() {

    }

    @Override
    public boolean isUsable() {
        return false;
    }
}
