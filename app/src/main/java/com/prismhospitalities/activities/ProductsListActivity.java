package com.prismhospitalities.activities;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.prismhospitalities.R;
import com.prismhospitalities.adapters.MenuProductAdapter;
import com.prismhospitalities.baseui.BaseAppCompactActivity;
import com.prismhospitalities.helpers.CartHelper;
import com.prismhospitalities.interfaces.IMenuProductView;
import com.prismhospitalities.interfaces.StringConstants;
import com.prismhospitalities.models.responses.AddCartResponse;
import com.prismhospitalities.models.responses.MenuItemResponseData;
import com.prismhospitalities.models.responses.MenuProductsResponse;
import com.prismhospitalities.models.responses.MenuProductsResponseData;
import com.prismhospitalities.presenters.MenuProductPresenter;
import com.prismhospitalities.utils.APIClient;
import com.prismhospitalities.utils.PrefUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class ProductsListActivity extends BaseAppCompactActivity implements IMenuProductView, MenuProductAdapter.ItemListener {

    private MenuItemResponseData menuItemResponseData;
    private MenuProductPresenter menuProductPresenter;
    private MenuProductAdapter menuProductAdapter;
    @BindView(R.id.rv_menuItems)
    RecyclerView rv_menuItems;
    @BindView(R.id.tv_heading)
    TextView tv_heading;

    @OnClick(R.id.iv_menu)
    public void onBackClick() {
        finish();
    }

    @OnClick(R.id.fab_refresh)
    public void onRefreshClick() {
        callMenuProductsApi();
    }


    @Override
    public int getActivityLayout() {
        return R.layout.activity_products_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        initRecyclerView();
        if (bundle != null) {
            menuItemResponseData = (MenuItemResponseData) bundle.getSerializable(StringConstants.RESPONSE);
            if (menuItemResponseData != null) {
                tv_heading.setText(menuItemResponseData.getCategoryname());
                callMenuProductsApi();
            }
        }
    }

    private void callMenuProductsApi() {
        if (menuProductPresenter == null)
            menuProductPresenter = new MenuProductPresenter(this, APIClient.getAPIService());
        menuProductPresenter.getMenuProducts(true, menuItemResponseData.getId());
    }


    private void initRecyclerView() {
        menuProductAdapter = new MenuProductAdapter(getApplicationContext(),
                null, this, CartHelper.getInstance().getCartList());
        // GridLayoutManager manager = new GridLayoutManager(ProductsListActivity.this, 2, GridLayoutManager.VERTICAL, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductsListActivity.this, RecyclerView.VERTICAL, false);
        rv_menuItems.setLayoutManager(linearLayoutManager);
        rv_menuItems.setItemAnimator(new DefaultItemAnimator());
        rv_menuItems.setAdapter(menuProductAdapter);
    }

    @Override
    public void getMenuProductsSuccess(MenuProductsResponse menuProductsResponse) {
        if (menuProductsResponse != null) {
            if (menuProductsResponse.getGetproducts() != null && menuProductsResponse.getGetproducts().size() > 0) {
                menuProductAdapter.setData(menuProductsResponse.getGetproducts());
            } else {
                showToast("No products found");
                menuProductAdapter.setData(null);
            }
        } else {
            showToast("failed to get the products");
            menuProductAdapter.setData(null);
        }
    }

    @Override
    public void getMenuProductsFailed() {
        showToast("failed to get the products");
        menuProductAdapter.setData(null);
    }

    @Override
    public void addToCartSuccess(AddCartResponse addCartResponse, int position) {

    }

    @Override
    public void addToCartFailed() {
        showToast("failed to add to cart");
    }


    @Override
    public boolean isUsable() {
        return false;
    }

    @Override
    public void onItemClick(int position, MenuProductsResponseData menuProductsResponseData) {
        if (menuProductsResponseData.getMenuDisplayImage() != null && !menuProductsResponseData.getMenuDisplayImage().isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(StringConstants.RESPONSE, menuProductsResponseData);
            goToActivity(ProductDetailsActivity.class, bundle);
        } else {
            showToast("No Details found for this product");
        }

    }

    @Override
    public void onAddCartClick(String quantity, MenuProductsResponseData menuProductsResponseData, int position) {
        if (menuProductPresenter == null)
            menuProductPresenter = new MenuProductPresenter(this, APIClient.getAPIService());
        menuProductPresenter.addOrRemoveFromCart(true, menuProductsResponseData.getId(),
                PrefUtils.getInstance().getUserId() + "", quantity, position);
    }
}
