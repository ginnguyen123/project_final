class AppBase{
    static DOMAIN_SERVER = location.origin;
    static API_SERVER = this.DOMAIN_SERVER + '/api';
    static API_PRODUCT = this.API_SERVER + '/products';
    static API_PRODUCT_IMPORT = this.API_SERVER + '/product-import';
    static API_BRAND = this.API_SERVER + '/brands';
    static API_CATEGORY = this.API_SERVER + '/categories';
    static API_DISCOUNT = this.API_SERVER + '/discounts';
    static SERVER_CLOUDINARY = "https://res.cloudinary.com";
    static CLOUDINARY_NAME = "/djkadtjxi";
    static CLOUDINARY_URL = this.SERVER_CLOUDINARY + this.CLOUDINARY_NAME + '/image/upload';
    static SCALE_IMAGE_W_80_H_80_Q_100 = 'c_limit,w_80,h_80,q_100';
    static SCALE_IMAGE_W_80_H_80_Q_85 = 'c_limit,w_80,h_80,q_85';

    static AlertMessageEn = class {
        static SUCCESS_CREATED = "Successful data generation !";
        static SUCCESS_UPDATED = "Data update successful !";
        static SUCCESS_DEPOSIT = "Deposit transaction successful !";
        static SUCCESS_WITHDRAW = "Withdrawal transaction successful !";
        static SUCCESS_TRANSFER = "Transfer transaction successful !";
        static SUCCESS_DEACTIVATE = "Deactivate the customer successfully !";

        static ERROR_400 = "The operation failed, please check the data again.";
        static ERROR_401 = "Unauthorized - Your access token has expired or is not valid.";
        static ERROR_403 = "Forbidden - You are not authorized to access this resource.";
        static ERROR_404 = "Not Found - The resource has been removed or does not exist";
        static ERROR_500 = "Internal Server Error - The server system is having problems or cannot be accessed.";

        static ERROR_CREATED = 'Adding new customer failed';

        static ERROR_LOADING_PROVINCE = "Loading list of provinces - cities failed !";
        static ERROR_LOADING_DISTRICT = "Loading list of district - ward failed !"
        static ERROR_LOADING_WARD = "Loading list of wards - communes - towns failed !";

        static ERROR_LOADING_PRODUCT = "Loading list of products failed !"
    }

    static AlertMessageVi = class {
        static SUCCESS_CREATED = "Tạo dữ liệu thành công !";
        static SUCCESS_UPDATED = "Cập nhật dữ liệu thành công !";
        static SUCCESS_DEPOSIT = "Giao dịch gửi tiền thành công !";
        static SUCCESS_WITHDRAW = "Giao dịch rút tiền thành công !";
        static SUCCESS_TRANSFER = "Giao dịch chuyển khoản thành công !";
        static SUCCESS_DEACTIVATE = "Hủy kích hoạt khách hàng thành công !";

        static ERROR_400 = "Thao tác không thành công, vui lòng kiểm tra lại dữ liệu.";
        static ERROR_401 = "Unauthorized - Access Token của bạn hết hạn hoặc không hợp lệ.";
        static ERROR_403 = "Forbidden - Bạn không được quyền truy cập tài nguyên này.";
        static ERROR_404 = "Not Found - Tài nguyên này đã bị xóa hoặc không tồn tại";
        static ERROR_500 = "Internal Server Error - Hệ thống Server đang có vấn đề hoặc không truy cập được.";

        static ERROR_CREATED = 'Thêm khách hàng mới không thành công';

        static ERROR_LOADING_PROVINCE = "Tải danh sách tỉnh - thành phố không thành công !";
        static ERROR_LOADING_DISTRICT = "Tải danh sách quận - phường - huyện không thành công !";
        static ERROR_LOADING_WARD = "Tải danh sách phường - xã - thị trấn không thành công !";
    }
    static IziToast = class {
        static showSuccessAlert(m) {
            iziToast.success({
                title: 'OK',
                position: 'topRight',
                timeout: 2500,
                message: m
            });
        }
        static showErrorAlert(m) {
            iziToast.error({
                title: 'Error',
                position: 'topRight',
                timeout: 2500,
                message: m
            });
        }
    }
    static showSuspendedConfirmDialog() {
        return Swal.fire({
            icon: 'warning',
            text: 'Are you sure to suspend the selected customer ?',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, please suspend this client !',
            cancelButtonText: 'Cancel',
        })
    }
    static showDeleteCartItemConfirmDialog() {
         return  Swal.fire({
            icon: 'warning',
            text: 'Bạn muốn xóa sản phẩm này ?',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Đồng ý',
            cancelButtonText: 'Không xóa',
        })
    }
    static showSuccessAlert(t) {
        Swal.fire({
            icon: 'success',
            title: t,
            position: 'top-end',
            showConfirmButton: false,
            timer: 1500
        })
    }

    static showErrorAlert(t) {
        Swal.fire({
            icon: 'error',
            title: 'Warning',
            text: t,
        })
    }


 static formDate(date) {
    let arr = date.split("-")
    date = arr[2] + "-" + arr[1] + "-" + arr[0]
    return date;
}
 static formMonth(month){
    let arr = month.split("-")
    month =  arr[1] + "-" + arr[0]
    return month;
}


    static formatNumber() {
        $(".num-space").number(true, 0, ',', ' ');
        $(".num-point").number(true, 0, ',', '.');
        $(".num-comma").number(true, 0, ',', ',');
    }

    static formatNumberSpace(x) {
        return x.toString().replace(/ /g, "").replace(/\B(?=(\d{3})+(?!\d))/g, " ");
    }

    static removeFormatNumberSpace(x) {
        return x.toString().replace(/ /g, "")
    }

    static formatTooltip() {
        $('[data-toggle="tooltip"]').tooltip();
    }

    static renderProduct(item){
        let image_thumbnail = `${AppBase.CLOUDINARY_URL}/${AppBase.SCALE_IMAGE_W_80_H_80_Q_85}/${item.avatar.fileFolder}/${item.avatar.fileName}`
        return `<tr id="tr_${item.id}">
                    <td>
                      <input type="checkbox" id="delete_${item.id}">
                    </td>
                    <td>${item.code}</td>
                    <td>
                        <img src="${image_thumbnail}"/>
                    </td>
                    <td>${item.title}</td>
                    <td>${item.price}</td>
                    <td>${item.category.name}</td>
                    <td>
                        <button class="btn btn-delete btn-sm delete" id="${item.id}" type="button" title="Xóa">
                            <i class="fas fa-trash-alt"></i>
                        </button>
                        
                        <button class="btn btn-edit btn-sm edit" id="${item.id}" type="button" title="Sửa" >
                            <i class="fas fa-edit"></i>
                        </button>
                    </td>
                </tr>`
    }

    static renderBrand(item){
        return `<option value="${item.id}">${item.name}</option>`;
    }

    static renderDiscount(item){
        return `<option value="${item.id}">${item.name}</option>`;
    }

    static renderCategory(item){
        if (item.categoryParentId == item.id)
            return `<option value="${item.id}">${item.name}</option>`;
    }

    static renderCategoryParent(item){
        if (item.categoryParentId != item.id)
            return `<option value="${item.id}">${item.name}</option>`;
    }

    static renderCustomer(item){
        //     do something
    }

    static renderProductImport(item){
        let str = this.formDate(item.date_added);
        return` <tr id="tr_${item.id}">
                            <td>
                                <input type="checkbox" id="delete_${item.id}">
                            </td>
                            <td>${item.code}</td>
                            <td>${item.size}</td>
                            <td>${item.color}</td>
                            <td>${new Intl.NumberFormat('vi-VN', {
                                    style: 'currency',
                                    currency: 'VND'
                            }).format(item.price)}</td>
                       
                            <td>${item.quantity}</td>
                            
                            <td><span class="badge btn-add btn-sm">${item.productStatus}</span></td>
                            
                            <td>${item.product.title}</td>
                            <td>${str}</td>
                            <td>
                                 <button class="btn btn-delete btn-sm delete" type="button" title="Xóa" data-id="${item.id}">
                                        <i class="fa fa-trash-alt"></i>
                                 </button>
                                 <button class="btn btn-edit btn-sm edit" type="button" title="Sửa" data-id="${item.id}" >
                                         <i class="fa fa-edit"></i>
                                 </button>
                            </td>
                        </tr>`
    }



}

class Brand{
    constructor(id, name){
        this.id = id;
        this.name = name;
    }
}

class Category{
    constructor(id, name, categoryParentId, categoryParentName){
        this.id = id;
        this.name = name;
        this.categoryParentId = categoryParentId;
        this.categoryParentName = categoryParentName;
    }
}

class Product{
    constructor(id, title, code, price, description, url, brand, category){
        this.id = id;
        this.title = title;
        this.code = code;
        this.price = price;
        this.description= description;
        this.url = url;
        this.brand = brand;
        this.category = category;
    }
}
class ProductImport{
    constructor(id, size, color, code,price, quantity, productStatus, product,date_added){
        this.id = id;
        this.size = size;
        this.color = color;
        this.code = code;
        this.price = price;
        this.quantity= quantity;
        this.productStatus = productStatus;
        this.product = product;
        this.date_added = date_added;

    }
}

class LocationRegion {
    constructor(id, provinceId, provinceName, districtId, districtName, wardId, wardName, address) {
        this.id = id;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.wardId = wardId;
        this.wardName = wardName;
        this.address = address;
    }
}

