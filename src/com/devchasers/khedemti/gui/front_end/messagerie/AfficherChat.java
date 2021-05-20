/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui.front_end.messagerie;

import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.devchasers.khedemti.entities.Message;
import com.devchasers.khedemti.services.ConversationService;
import com.devchasers.khedemti.services.MessageService;

/**
 *
 * @author Grim
 */
public class AfficherChat extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
    int conversationActuelleId;

    Form previous;

    public AfficherChat(int conversationId, String titreChat, Image photoUser, Form previous) {
        super(new BorderLayout());

        this.previous = previous;

        ImageViewer imageChat = new ImageViewer(photoUser);
        imageChat.setUIID("imageChat");
        imageChat.setFocusable(false);
        imageChat.setPreferredSize(new Dimension(150, 150));
        Container container = new Container(new BoxLayout(BoxLayout.X_AXIS));
        container.addAll(imageChat, new Label(titreChat));

        Button btnSupprimer = new Button("Supprimer");
        btnSupprimer.addActionListener(l -> {

            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer votre conversation ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                ConversationService.getInstance().supprimerConversation(conversationId);
                dlg.dispose();
                ((AfficherConversations) previous).refresh();
                previous.showBack();
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(900, 900, 150, 150);

        });

        super.getToolbar().add(BorderLayout.CENTER, container);
        super.getToolbar().add(BorderLayout.EAST, btnSupprimer);
        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

        this.conversationActuelleId = conversationId;

        addGUIs();
        addActions();

    }

    Container messagesContainer, nouveauMessageContainer;
    TextField contenuMessage;
    Button envoyerMessage;

    private void addGUIs() {
        messagesContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        for (Message message : MessageService.getInstance().recupererMessagesParConversation(conversationActuelleId)) {
            messagesContainer.add(creerMessage(message));
        }

        contenuMessage = new TextField();
        contenuMessage.setUIID("tfMessage");
        contenuMessage.setTooltip("Nouveau message");
        contenuMessage.setPreferredW(Display.getInstance().getDisplayWidth() / 2);

        envoyerMessage = new Button("Envoyer");
        envoyerMessage.setUIID("buttonEnvoyer");

        nouveauMessageContainer = new Container(new BorderLayout());
        nouveauMessageContainer.add(BorderLayout.WEST, contenuMessage);
        nouveauMessageContainer.add(BorderLayout.EAST, envoyerMessage);

        this.add(BorderLayout.NORTH, messagesContainer);
        this.add(BorderLayout.SOUTH, nouveauMessageContainer);
    }

    private void addActions() {
        envoyerMessage.addActionListener(l -> {
            Message message = new Message(conversationActuelleId, contenuMessage.getText());
            message.setEstProprietaire(true);

            MessageService.getInstance().ajouterMessage(message);
            messagesContainer.add(creerMessage(message));

            contenuMessage.setText("");

            this.refreshTheme();
        });
    }

    private SpanLabel creerMessage(Message message) {
        
        ((AfficherConversations) previous).refresh();
        
        if (message.getEstProprietaire()) {
            SpanLabel messageExpediteur = new SpanLabel("message exp : " + message.getContenu());
            messageExpediteur.setUIID("messageExpediteur");
            messageExpediteur.setTextUIID("textWhite");
            return messageExpediteur;
        } else {
            SpanLabel messageDestinataire = new SpanLabel("message dest : " + message.getContenu());
            messageDestinataire.setUIID("messageDestinataire");
            messageDestinataire.setTextUIID("textWhite");
            return messageDestinataire;
        }
    }
}
