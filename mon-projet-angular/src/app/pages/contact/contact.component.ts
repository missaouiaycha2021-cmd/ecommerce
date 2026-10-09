import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [FormsModule, RouterLink, RouterLinkActive],
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.css',
})
export class ContactComponent {
  nom = '';
  email = '';
  sujet = '';
  message = '';
  envoye = false;

  envoyer(): void {
    this.envoye = true;
    this.nom = this.email = this.sujet = this.message = '';
  }
}