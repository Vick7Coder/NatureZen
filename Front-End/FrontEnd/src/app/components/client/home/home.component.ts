import { Component, OnInit } from '@angular/core';
import { faHeart, faShoppingBag, faRetweet } from '@fortawesome/free-solid-svg-icons';
import { MessageService } from 'primeng/api';
import { CartService } from 'src/app/_service/cart.service';
import { ProductService } from 'src/app/_service/product.service';
import { WishlistService } from 'src/app/_service/wishlist.service';
import { CategoryService } from 'src/app/_service/category.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: [MessageService]
})
export class HomeComponent implements OnInit {

  heart = faHeart;
  bag = faShoppingBag;
  retweet = faRetweet;

  listProductNewest: any;
  listProductPrice: any;

  showDepartment = true;

  listCategoryEnabled: any;

  firstCategoryImages: { [key: number]: string } = {};

  constructor(
    private productSerive: ProductService,
    private cartService: CartService,
    private wishlistService: WishlistService,
    private messageService: MessageService,
    private categoryService: CategoryService,
  ) { }

  ngOnInit(): void {
    this.getListProduct();
    this.getCategoryEnbled();
  }

  getListProduct() {
    this.productSerive.getListProductNewest(8).subscribe({
      next: res => {
        this.listProductNewest = res;
        this.getFirstCategoryImages();
      }, error: err => {
        console.log(err);
      }
    });
    this.productSerive.getListProductByPrice().subscribe({
      next: res => {
        this.listProductPrice = res;
      }, error: err => {
        console.log(err);
      }
    });
  }

  getCategoryEnbled() {
    this.categoryService.getListCategoryEnabled().subscribe({
      next: res => {
        this.listCategoryEnabled = res;
      }, error: err => {
        console.log(err);
      }
    });
  }

  getFirstCategoryImages() {
    this.listCategoryEnabled.forEach((category: any) => {
      const firstImage = this.listProductNewest.find((product: any) => product.category.id === category.id)?.images[0]?.data;
      console.log('First Image for Category ' + category.id + ':', firstImage);
      this.firstCategoryImages[category.id] = firstImage;
    });
  }

  addToCart(item: any) {
    this.cartService.getItems();
    this.showSuccess('Đã thêm vào giỏ hàng');
    this.cartService.addToCart(item, 1);
  }

  addToWishList(item: any) {
    if (!this.wishlistService.productInWishList(item)) {
      this.showSuccess('Đã thêm vào danh sách yêu thích');
      this.wishlistService.addToWishList(item);
    }
  }

  showSuccess(text: string) {
    this.messageService.add({ severity: 'success', summary: 'Success', detail: text });
  }

  showError(text: string) {
    this.messageService.add({ severity: 'error', summary: 'Error', detail: text });
  }

  showWarn(text: string) {
    this.messageService.add({ severity: 'warn', summary: 'Warn', detail: text });
  }
}
