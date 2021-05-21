package com.devchasers.khedemti.gui.front_end.candidat;

import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.ext.filechooser.FileChooser;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.CN;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.devchasers.khedemti.entities.Competence;
import com.devchasers.khedemti.entities.Education;
import com.devchasers.khedemti.entities.ExperienceDeTravail;
import com.devchasers.khedemti.services.CompetenceService;
import com.devchasers.khedemti.services.EducationService;
import com.devchasers.khedemti.services.ExperienceDeTravailService;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class AfficherProfil extends Form {

    Container btnsContainer;
    Button btnRetour, btnAjouter, btnModifier, btnSupprimer;
    public static Competence competenceActuelle = null;
    public static Education educationActuelle = null;
    public static ExperienceDeTravail experienceDeTravailActuelle = null;

    Container imageContainer;

    Button btnAjouterImage;
    CheckBox multiSelect;

    Image imageCandidat;

    Resources theme = UIManager.initFirstTheme("/theme");

    public AfficherProfil() {
        super();
        addGUIs();
        addActions();
        getToolbar().hideToolbar();
    }

    public void refreshProfil() {
        this.removeAll();
        addGUIs();
        addActions();
        this.refreshTheme();
    }

    private void addGUIs() {

        multiSelect = new CheckBox("Multi-select");
        btnAjouterImage = new Button("Ajouter une image");

        imageContainer = new Container();

        this.addAll(btnAjouterImage, imageContainer);

        setLayout(BoxLayout.y());
        Button btnAddEducation = new Button("Ajouter une education ");
        btnAddEducation.addActionListener(e -> new AjouterEducation(this).show());

        this.add(btnAddEducation);

        ArrayList<Education> listEducation = EducationService.getInstance().recupererEducation();

        for (Education education : listEducation) {
            this.add(creerEducation(education));
        }

        setLayout(BoxLayout.y());
        Button btnAddExperienceDeTravail = new Button("Ajouter une experience de travail ");
        btnAddExperienceDeTravail.addActionListener(e -> new AjouterExperienceDeTravail(this).show());

        this.add(btnAddExperienceDeTravail);

        ArrayList<ExperienceDeTravail> listExperienceDeTravail = ExperienceDeTravailService.getInstance().recupererExperienceDeTravail();

        for (ExperienceDeTravail experience_de_travail : listExperienceDeTravail) {
            this.add(creerExperienceDeTravail(experience_de_travail));
        }

        setLayout(BoxLayout.y());
        Button btnAddCompetence = new Button("Ajouter une competence ");
        btnAddCompetence.addActionListener(e -> new AjouterCompetenceForm(this).show());

        this.add(btnAddCompetence);

        ArrayList<Competence> listCompetence = CompetenceService.getInstance().recupererCompetence();

        for (Competence competence : listCompetence) {
            this.add(creerCompetence(competence));
        }

        creerImage();
    }

    private Container creerCompetence(Competence competence) {
        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerLevel");
        Label labelName = new Label("Nom : " + competence.getName());
        Slider sliderLevel = new Slider();
        sliderLevel.setProgress(competence.getLevel());

        btnsContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        btnsContainer.setUIID("buttonsContainer");
        btnsContainer.setPreferredH(200);

        btnModifier = new Button("Modifier");
        btnModifier.setUIID("actionButton");

        btnSupprimer = new Button("Supprimer");
        btnSupprimer.setUIID("actionButton");

        btnModifier.addActionListener(action -> {
            competenceActuelle = competence;
            new ModifierCompetence(this).show();
        });
        btnSupprimer.addActionListener(action -> {

            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer votre competence ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                CompetenceService.getInstance().supprimerCompetence(competence);
                competenceActuelle = null;
                dlg.dispose();

                container.remove();

                this.refreshTheme();
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            // Dimension pre = dlg.getContentPane().getPreferredSize();
            // dlg.show(0, 0, Display.getInstance().getDisplayWidth() - (pre.getWidth() + pre.getWidth() / 6), 0);
            dlg.show(900, 900, 150, 150);

        });
        btnsContainer.addAll(btnModifier, btnSupprimer);

        container.addAll(labelName, sliderLevel, btnsContainer);
        return container;
    }

    private Container creerExperienceDeTravail(ExperienceDeTravail experience_de_travail) {
        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerLevel");

        ImageViewer imageExperience = new ImageViewer(theme.getImage("suitcase.png"));
        Label labelDescription = new Label("Description : " + experience_de_travail.getDescription());
        Label labeltitreEmploi = new Label("Titre emploi : " + experience_de_travail.getTitreEmploi());
        Label labelnomEntreprise = new Label("Nom entreprise : " + experience_de_travail.getNomEntreprise());
        Label labelVille = new Label("Ville : " + experience_de_travail.getVille());
        Label labelDuree = new Label("Duree : " + experience_de_travail.getDuree());

        btnsContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        btnsContainer.setUIID("buttonsContainer");
        btnsContainer.setPreferredH(200);

        btnModifier = new Button("Modifier");
        btnModifier.setUIID("actionButton");
        btnSupprimer = new Button("Supprimer");
        btnSupprimer.setUIID("actionButton");

        btnModifier.addActionListener(action -> {
            experienceDeTravailActuelle = experience_de_travail;
            new ModifierExperienceDeTravail(this).show();
        });
        btnSupprimer.addActionListener(action -> {

            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer votre experience_de_travail ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                ExperienceDeTravailService.getInstance().supprimerExperienceDeTravail(experience_de_travail);
                experienceDeTravailActuelle = null;
                dlg.dispose();

                container.remove();

                this.refreshTheme();
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            //Dimension pre = dlg.getContentPane().getPreferredSize();
            //dlg.show(0, 0, Display.getInstance().getDisplayWidth() - (pre.getWidth() + pre.getWidth() / 6), 0);
            dlg.show(900, 900, 150, 150);

        });
        btnsContainer.addAll(btnModifier, btnSupprimer);
        container.addAll(imageExperience, labeltitreEmploi, labelnomEntreprise, labelVille, labelDuree, labelDescription, btnsContainer);
        return container;
    }

    private Container creerEducation(Education education) {
        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerLevel");

        ImageViewer imageEducation = new ImageViewer(theme.getImage("award.png"));
        Label labelDescription = new Label("Description : " + education.getDescription());
        Label labelniveauEducation = new Label("Niveau d'education : " + education.getNiveauEducation());
        Label labelEtablissement = new Label("Etablissement : " + education.getEtablissement());
        Label labelFiliere = new Label("Filiere : " + education.getFiliere());
        Label labelVille = new Label("Ville : " + education.getVille());
        Label labelDuree = new Label("Duree : " + education.getDuree() + " Années");

        btnsContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        btnsContainer.setUIID("buttonsContainer");
        btnsContainer.setPreferredH(200);

        btnModifier = new Button("Modifier");
        btnModifier.setUIID("actionButton");
        btnSupprimer = new Button("Supprimer");
        btnSupprimer.setUIID("actionButton");

        btnModifier.addActionListener(action -> {
            educationActuelle = education;
            new ModifierEducation(this).show();
        });
        btnSupprimer.addActionListener(action -> {

            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer votre education ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                EducationService.getInstance().supprimerEducation(education);
                educationActuelle = null;
                dlg.dispose();
                container.remove();

                this.refreshTheme();
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            //Dimension pre = dlg.getContentPane().getPreferredSize();
            //dlg.show(0, 0, Display.getInstance().getDisplayWidth() - (pre.getWidth() + pre.getWidth() / 6), 0);
            dlg.show(900, 900, 150, 150);

        });
        btnsContainer.addAll(btnModifier, btnSupprimer);

        container.addAll(imageEducation, labelniveauEducation, labelEtablissement,
                labelFiliere, labelVille, labelDuree, labelDescription, btnsContainer);
        return container;
    }

    private void addActions() {

    }

    protected String saveFileToDevice(String hi, String ext) throws IOException {
        URI uri;
        try {
            uri = new URI(hi);
            String path = uri.getPath();
            int index = hi.lastIndexOf("/");
            hi = hi.substring(index + 1);
            return hi;
        } catch (URISyntaxException ex) {
        }
        return "test";
    }

    private void creerImage() {
        btnAjouterImage.addActionListener(
                (ActionEvent e) -> {
                    if (FileChooser.isAvailable()) {
                        FileChooser.setOpenFilesInPlace(true);
                        FileChooser.showOpenDialog(multiSelect.isSelected(), ".jpg, .jpeg, .png/plain", (ActionEvent e2) -> {
                            if (e2 == null || e2.getSource() == null) {
                                this.add("No file was selected");
                                this.revalidate();
                                return;
                            }
                            if (multiSelect.isSelected()) {
                                String[] paths = (String[]) e2.getSource();
                                for (String path : paths) {
                                    System.out.println(path);
                                    CN.execute(path);
                                }
                                return;
                            }

                            String file = (String) e2.getSource();
                            if (file == null) {
                                add("No file was selected");
                                revalidate();
                            } else {

                                try {
                                    imageCandidat = Image.createImage(file).scaledHeight(500);

                                    imageContainer.removeAll();
                                    ImageViewer imageViewer = new ImageViewer(imageCandidat);
                                    imageViewer.setUIID("imageCenter");
                                    imageContainer.add(imageViewer);

                                    String imageFile = FileSystemStorage.getInstance().getAppHomePath() + "photo.png";

                                    this.refreshTheme();

                                    try (OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
                                        System.out.println(imageFile);
                                        ImageIO.getImageIO().save(imageCandidat, os, ImageIO.FORMAT_PNG, 1);
                                    } catch (IOException err) {
                                    }
                                } catch (IOException ex) {
                                }

                                String extension = null;
                                if (file.lastIndexOf(".") > 0) {
                                    extension = file.substring(file.lastIndexOf(".") + 1);
                                    StringBuilder hi = new StringBuilder(file);
                                    if (file.startsWith("file://")) {
                                        hi.delete(0, 7);
                                    }
                                    int lastIndexPeriod = hi.toString().lastIndexOf(".");
                                    Log.p(hi.toString());
                                    String ext = hi.toString().substring(lastIndexPeriod);
                                    String hmore = hi.toString().substring(0, lastIndexPeriod - 1);
                                    try {
                                        String namePic = saveFileToDevice(file, ext);
                                        System.out.println(namePic);
                                    } catch (IOException ex) {
                                    }

                                    revalidate();

                                }
                            }
                        });
                    }
                }
        );
    }
}
