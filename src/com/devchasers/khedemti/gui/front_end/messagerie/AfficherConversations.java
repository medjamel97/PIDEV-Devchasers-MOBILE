/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui.front_end.messagerie;

import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.devchasers.khedemti.MainApp;
import com.devchasers.khedemti.entities.Contact;
import com.devchasers.khedemti.entities.Conversation;
import com.devchasers.khedemti.services.ContactsService;
import com.devchasers.khedemti.services.ConversationService;

/**
 *
 * @author Grim
 */
public class AfficherConversations extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");

    public AfficherConversations(Form previous) {
        super("Messagerie", new BoxLayout(BoxLayout.Y_AXIS));
        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

        addGUIs();
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        this.refreshTheme();
    }

    Container contactsContainer;

    private void addGUIs() {
        contactsContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        contactsContainer.setUIID("contactsContainer");
        contactsContainer.setScrollableX(true);
        contactsContainer.setScrollVisible(true);

        for (Contact contact : ContactsService.getInstance().recupererContacts()) {
            if (contact.getUserId() != MainApp.getSession().getId()) {
                contactsContainer.add(creerContact(contact));
            }
        }

        this.add(contactsContainer);

        for (Conversation conversation : ConversationService.getInstance().recupererConversations()) {
            this.add(creerConversation(conversation));
        }
    }

    private Container creerContact(Contact contact) {

        ImageViewer photoContact;
        try {
            String url = contact.getIdPhoto();
            photoContact = new ImageViewer(
                    URLImage.createToStorage(
                            EncodedImage.createFromImage(theme.getImage("default.jpg"), false),
                            url,
                            url,
                            URLImage.RESIZE_SCALE
                    )
            );
        } catch (Exception e) {
            System.out.println("Erreur recuperation image .. cause : " + e.getCause());
            photoContact = new ImageViewer(theme.getImage("default.jpg"));
        }
        photoContact.setPreferredSize(new Dimension(320, 320));
        photoContact.setFocusable(false);
        photoContact.setUIID("photoContact");

        Label labelNomContact = new Label(contact.getNomComplet());

        Container contactContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        Button btnAfficherContact = new Button("Contacter");
        btnAfficherContact.setUIID("buttonConsulter");

        Image imageCandidat = photoContact.getImage();
        btnAfficherContact.addActionListener(l -> {
            Conversation conversation = ConversationService.getInstance().ajouterConversation(contact.getUserId());
            creerConversation(conversation);
            this.refreshTheme();

            new AfficherChat(conversation.getId(), conversation.getNom(), imageCandidat, this).show();
        });

        contactContainer.addAll(photoContact, labelNomContact, btnAfficherContact);

        return contactContainer;
    }

    private Container creerConversation(Conversation conversation) {

        Container conversationContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));

        ImageViewer photoUser;
        try {
            String url = conversation.getIdPhoto();
            photoUser = new ImageViewer(
                    URLImage.createToStorage(
                            EncodedImage.createFromImage(theme.getImage("default.jpg"), false),
                            url,
                            url,
                            URLImage.RESIZE_SCALE
                    )
            );
        } catch (Exception e) {
            System.out.println("Erreur recuperation image .. cause : " + e.getCause());
            photoUser = new ImageViewer(theme.getImage("default.jpg"));
        }
        photoUser.setPreferredSize(new Dimension(200, 200));
        photoUser.setUIID("photoUserConv");

        Label labelNomConversation = new Label(conversation.getNom());
        Label labelDernierMessage = new Label(conversation.getDernierMessage());

        Container containerInfos = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        containerInfos.setUIID("infosContainer");
        containerInfos.addAll(labelNomConversation, labelDernierMessage);

        Button btnSupprimer = new Button("Supprimer");
        btnSupprimer.addActionListener(l -> {

            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer votre conversation ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                ConversationService.getInstance().supprimerConversation(conversation.getId());
                dlg.dispose();

                conversationContainer.remove();

                this.refreshTheme();
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(900, 900, 150, 150);

        });

        conversationContainer.setUIID("conversationContainer");
        conversationContainer.addAll(photoUser, containerInfos, btnSupprimer);

        Button btnAfficherConversation = new Button("tessst");

        final Image photoUserForChat = photoUser.getImage();
        btnAfficherConversation.addActionListener(l -> {
            new AfficherChat(conversation.getId(), conversation.getNom(), photoUserForChat, this).show();
        });

        conversationContainer.setLeadComponent(btnAfficherConversation);

        return conversationContainer;
    }
}
