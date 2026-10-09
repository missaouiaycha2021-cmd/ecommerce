import { Component, OnInit, inject } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { ProduitService } from '../../services/produit.service';

@Component({
  selector: 'app-produits',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, CurrencyPipe],
  templateUrl: './produits.component.html',
  styleUrl: './produits.component.css'
})
export class ProduitsComponent implements OnInit {
  private service = inject(ProduitService);
  produits: any[] = [];

  ngOnInit(): void {
    this.service.getAll().subscribe({
      next: (data) => (this.produits = data),
      error: (err) => console.error('Erreur API', err)
    });
  }
}