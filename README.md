# Playlist-Spotify-Generator - Zazou

## Introduction

> Android workshop - DMII Gobelins

> Zazou let you create and customise playlist from spotify.



#Installation
### depuis le repo
- cloner le repo
- builder le projet
- lancer

### depuis l’APK
- installer et lancer l’APK

#Problèmes rencontrées

- Garder l’utilisateur informé → Utilisation des Toasts
Pour préserver les bonnes pratiques d’UX/UI, nous utilisons des Toasts pour signaler à l’utilisateur ce qu’il se passe sur l’application pendant que celle-ci charge son contenu.

- Affichage des images → Glide
Glide nous permet de télécharger des images à partir d’une URL et de les mettre en cache pour pouvoir les ré-afficher ultérieurement si besoin.

- Ouverture des différentes fragments → FragmentManager
Pour conserver une architecture de projet correcte, nous avons mis en place un fragment manager, qui va être capable d’appeler les fragments et de les afficher en fonction de paramètres souhaités.

- Gestion d’un player présent sur plusieurs fragments → les FrameLayout
Nous utilisons 2 FrameLayout, qui nous permettent à la fois d’afficher le contenu de nos principaux fragments et d’afficher le player

- Supprimer une playlist → se désabonner
l’API Web de Spotify ne permet pas de supprimer les playlist directement, afin de simuler son comportement, nous nous désabonnons de la playlist, qui ne sera donc plus affiché dans l’application


Versions de SDK -> 27
Minsdkversion : 16
