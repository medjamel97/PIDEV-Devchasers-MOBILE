/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui.front_end.publication;

import com.codename1.components.InteractionDialog;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.devchasers.khedemti.MainApp;
import com.devchasers.khedemti.entities.CandidatureOffre;
import com.devchasers.khedemti.entities.Commentaire;
import com.devchasers.khedemti.entities.Publication;
import com.devchasers.khedemti.services.CommentaireService;
import com.devchasers.khedemti.services.PublicationService;
import com.devchasers.khedemti.services.UserService;
import java.util.ArrayList;

/**
 *
 * @author Grim
 */
public class AfficherToutPublication extends Form {

    public static Publication publicationActuelle = null;
    public static CandidatureOffre candidatureOffreActuelle = null;
    public static Resources theme = UIManager.initFirstTheme("/theme");

    public static String nomOffreActuelle, nomSocieteActuelle;

    Button btnAjouter;

    public AfficherToutPublication(Form previous) {
        super("Publications sur " + nomOffreActuelle + " de la societe " + nomSocieteActuelle, new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
        addActions();
        getToolbar().hideToolbar();
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        addActions();
        this.refreshTheme();
    }

    private void addGUIs() {
        btnAjouter = new Button("Nouvelle publication");
        btnAjouter.setUIID("newButton");

        this.add(btnAjouter);

        ArrayList<Publication> listPublications = PublicationService.getInstance().recupererPublications();
        for (int i = 0; i < listPublications.size(); i++) {
            this.add(creerPublication(listPublications.get(i)));
        }
    }

    private void addActions() {
        btnAjouter.addActionListener(action -> {
            new AjouterPublication(this).show();
        });
    }

    private Component creerPublication(Publication publication) {
        Container publicationModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        publicationModel.setUIID("publicationContainer");

        Label labelTitre = new Label((String) publication.getTitre());
        labelTitre.setUIID("defaultLabel");
        SpanLabel spanLabelDescription = new SpanLabel(publication.getDescription());
        spanLabelDescription.setTextUIID("description");
        Label labelDateCreation = new Label(publication.getDate());
        labelDateCreation.setUIID("dateLabel");

        Container btnsContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        btnsContainer.setUIID("buttonsContainer");
        btnsContainer.setPreferredH(200);

        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("actionButton");
        Button btnSupprimer = new Button("Supprimer");
        btnSupprimer.setUIID("actionButton");
        Button btnEmail = new Button("Envoyer mail");
        btnEmail.setUIID("actionButton");

        btnEmail.addActionListener(email -> {
            Message m = new Message("Body of message");
            m.getAttachments().put("API mail", "text/plain");
            Display.getInstance().sendMessage(new String[]{
                UserService.getInstance().recupererUserParId(publication.getUserId()).getEmail()
            }, "Veillez saisir le Mail", m);
        });

        btnModifier.addActionListener(action -> {

            publicationActuelle = publication;
            new ModifierPublication(this).show();
            PublicationService.getInstance().modifierPublication(publication);

            addGUIs();
        });
        btnSupprimer.addActionListener(action -> {

            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer votre publication ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                PublicationService.getInstance().supprimerPublication(publication);
                publicationActuelle = null;
                dlg.dispose();
                this.removeAll();
                addGUIs();
                this.refreshTheme();
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            //Dimension pre = dlg.getContentPane().getPreferredSize();
            //dlg.show(0, 0, Display.getInstance().getDisplayWidth() - (pre.getWidth() + pre.getWidth() / 6), 0);
            dlg.show(900, 900, 150, 150);

        });

        Container commentaireContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        System.out.println(publication.getId());
        ArrayList<Commentaire> listCommentaires = CommentaireService.getInstance().recupererCommentaires(publication.getId());
        for (int i = 0; i < listCommentaires.size(); i++) {
            commentaireContainer.add(creerCommentaire(listCommentaires.get(i)));
        }

        btnsContainer.addAll(btnModifier, btnSupprimer, btnEmail);

        Container ajoutContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));

        TextField tfcomment = new TextField();
        tfcomment.setTooltip("ajouter Commentaire");
        tfcomment.setPreferredW(600);
        Button btnEnvoyer = new Button("Ajouter");

        btnEnvoyer.addActionListener(ajoutcomm -> {
            if (tfcomment.getText().equals("")) {
                Dialog.show("Commentaire vide", "", new Command("Ok"));

            } else {

                System.out.println("11");
                int responseCode = CommentaireService.getInstance().ajouterCommentaire(new Commentaire(
                        publication.getId(),
                        MainApp.getSession().getCandidatId(),
                        tfcomment.getText()
                ));

                //    int responseCode = PublicationService.getInstance().ajouterPublication(new Publication
                // (  MainApp.getSession().getCandidatId(), tfTitre.getText(), tfDescription.getText()  ));
                System.out.println("22");

                commentaireContainer.add(creerCommentaire(new Commentaire(publication.getId(),
                        MainApp.getSession().getId(), tfcomment.getText())));
                ToastBar.getInstance().setPosition(BOTTOM);
                ToastBar.Status status = ToastBar.getInstance().createStatus();
                status.setShowProgressIndicator(true);
                //status.setIcon(res.getImage("done.png").scaledSmallerRatio(Display.getInstance().getDisplayWidth()/10, Display.getInstance().getDisplayWidth()/15));
                status.setMessage("  Commentaire ajouté avec succès");
                status.setExpires(10000);
                status.show();

                refreshTheme();

                tfcomment.setText("");

                this.refreshTheme();
//             LocalNotification n = new LocalNotification();
//        n.setId("notif");
//        n.setAlertBody("Commentaire ajoutè avec succés");
//        n.setAlertTitle("Khedemti Team!");
//        n.setAlertSound("/notification_sound_party.mp3"); //file name must begin with notification_sound
//         
//                        Display.getInstance().scheduleLocalNotification(
//                n,
//                System.currentTimeMillis()  + 10 * 1000, // fire date/time
//             //   System.currentTimeMillis()
//                LocalNotification.REPEAT_MINUTE // Whether to repeat and what frequency
//           
//        );

                //  Display.getInstance().scheduleLocalNotification(n, 10, 10);
                System.out.println(System.currentTimeMillis());

            }
        });

        ajoutContainer.addAll(tfcomment, btnEnvoyer);

        publicationModel.addAll(labelDateCreation, labelTitre, spanLabelDescription, btnsContainer, commentaireContainer,
                ajoutContainer);

        return publicationModel;

    }

    private Component creerCommentaire(Commentaire commentaire) {
        Container commentaireModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        commentaireModel.setUIID("commentaireContainer");

        SpanLabel spanLabelDescriptionCom = new SpanLabel(commentaire.getDescription());
        spanLabelDescriptionCom.setTextUIID("description");

        Container btnsContainerCom = new Container(new BoxLayout(BoxLayout.X_AXIS));
        btnsContainerCom.setUIID("buttonsContainer");

        Button btnModifierCom = new Button("Modifier");
        btnModifierCom.setUIID("actionButton");
        btnModifierCom.setPreferredSize(new Dimension(400, 100));
        Button btnSupprimerCom = new Button("Supprimer");
        btnSupprimerCom.setUIID("actionButton");
        btnSupprimerCom.setPreferredSize(new Dimension(400, 100));

        btnModifierCom.addActionListener(action -> {
            System.out.println("msgggggggg");
            TextField descrip = new TextField(commentaire.getDescription());
            Button modifier = new Button("Modifier commentaire");
            modifier.addActionListener(acceptmodif
                    -> {
                if (descrip.getText().equals("")) {
                    Dialog.show("Commentaire vide", "", new Command("Ok"));

                } else if (descrip.getText().equals(commentaire.getDescription())) {
                    Dialog.show("Votre commentaire a restè le même", "", new Command("Ok"));
                    descrip.remove();
                    modifier.remove();
                    this.refreshTheme();
                } else {

                    commentaire.setDescription(descrip.getText());
                    CommentaireService.getInstance().modifierCommentaire(commentaire);
                    spanLabelDescriptionCom.setText(descrip.getText());
                    descrip.remove();
                    modifier.remove();
                    this.refreshTheme();
                }
            }
            );
            commentaireModel.addAll(descrip, modifier);

            //new ManipulerCommentaire(this).show();
            this.refreshTheme();
        });
        btnSupprimerCom.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer votre commentaire ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                CommentaireService.getInstance().supprimerCommentaire(commentaire);
                dlg.dispose();
                spanLabelDescriptionCom.remove();
                btnsContainerCom.remove();

                this.refreshTheme();
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            Dimension pre = dlg.getContentPane().getPreferredSize();
            dlg.show(900, 900, 150, 150);

        });

        btnsContainerCom.addAll(btnModifierCom, btnSupprimerCom);

        commentaireModel.addAll(spanLabelDescriptionCom, btnsContainerCom);

        return commentaireModel;
    }
}
