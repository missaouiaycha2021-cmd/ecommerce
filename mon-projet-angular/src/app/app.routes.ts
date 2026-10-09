//Il associe une URL à un composant.
import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { AboutComponent } from './pages/about/about.component';
import { ServicesComponent } from './pages/services/services.component';
import { BlogComponent } from './pages/blog/blog.component';
import { ContactComponent } from './pages/contact/contact.component';
import { CategoriesComponent } from './admin/categories/categories.component';
import { ClientsComponent } from './admin/clients/clients.component';
import { DashboardComponent } from './admin/dashboard/dashboard.component';
import { CommandesComponent } from './admin/commandes/commandes.component';
import { LoginComponent } from './admin/login/login.component';
import { ProduitsComponent } from './admin/produits/produits.component';

export const routes: Routes = [

{path: '' , component: HomeComponent },
{ path: 'about', component: AboutComponent },
{ path: 'services', component: ServicesComponent},
{ path: 'blog', component: BlogComponent},
{ path: 'contact', component: ContactComponent},

{ path: 'admin/categories', component: CategoriesComponent},
{ path: 'admin/clients', component: ClientsComponent},
{ path: 'admin/commandes', component: CommandesComponent},
{ path: 'admin/dashboard', component: DashboardComponent},
{ path: 'admin/login', component: LoginComponent},
{ path: 'admin/produits', component: ProduitsComponent},
];
