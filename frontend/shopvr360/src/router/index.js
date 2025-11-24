import { createRouter, createWebHistory } from 'vue-router'

// Client Layout
import ClientLayout from '../layouts/client/ClientLayout.vue'

// Admin Layout
import AdminLayout from '../layouts/admin/AdminLayout.vue'

// Client Pages
import Main from '../pages/client/product.vue'
import Login from '../pages/client/login.vue'
import Register from '../pages/client/register.vue'

// Admin Pages
import Dashboard from '../pages/admin/dashboard.vue'
import User from '../pages/admin/user/User.vue'
import Role from '../pages/admin/user/Role.vue'
import AddUser from '../pages/admin/user/addUser.vue'
import EditUser from '../pages/admin/user/editUser.vue'
import ManageProductList  from '../pages/admin/ProductList/ManageProductList.vue'
import product from '../pages/admin/product/product.vue';

const routes = [
  // CLIENT
  {
    path: '/',
    component: ClientLayout,
    children: [
      {
        path: '',
        name: 'home',
        component: Main
      }
    ]
  },
  { path: '/login', name: 'login', component: Login },
  { path: '/register', name: 'register', component: Register },

  // ADMIN
  {
    path: '/admin',
    component: AdminLayout,
    children: [
      {
        path: 'dashboard', 
        name: 'admin-dashboard', 
        component: Dashboard,
        meta: { title: 'Dashboard' }
      },
      {
        path: 'users',   
        name: 'admin-users',
        component: User,
        meta: { title: 'Quản lý Users' }
      },
      {
        path: 'roles',   
        name: 'admin-roles',
        component: Role,
        meta: { title: 'Quản lý Roles' }
      },
      {
        path: 'add-user',   
        name: 'admin-add-user',
        component: AddUser,
        meta: { title: 'Thêm User' }
      },
      {
        path: 'edit-user',   
        name: 'admin-edit-user',
        component: EditUser,
        meta: { title: 'Sửa User' }
      },
      {
        path: 'categories',   
        name: 'ManageProductList',
        component: ManageProductList,
        meta: { title: 'Quản lý danh mục sản phẩm ' }
      },
      {
        path: 'products',
        name: 'admin-products',
        component: product,
        meta: { title: 'Quản lý sản phẩm' }
      }
      
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
