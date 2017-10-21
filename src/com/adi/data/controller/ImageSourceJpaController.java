/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.data.controller;

import com.adi.data.controller.exceptions.IllegalOrphanException;
import com.adi.data.controller.exceptions.IllegalOrphanException;
import com.adi.data.controller.exceptions.NonexistentEntityException;
import com.adi.data.controller.exceptions.NonexistentEntityException;
import com.adi.data.controller.exceptions.PreexistingEntityException;
import com.adi.data.controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.adi.data.entity.Image;
import com.adi.data.entity.ImageSource;
import com.adi.data.entity.Tag;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Caique
 */
public class ImageSourceJpaController implements Serializable {

    public ImageSourceJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ImageSource imageSource) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (imageSource.getTagList() == null) {
            imageSource.setTagList(new ArrayList<Tag>());
        }
        if (imageSource.getImageList() == null) {
            imageSource.setImageList(new ArrayList<Image>());
        }
        List<String> illegalOrphanMessages = null;
        Image imageOrphanCheck = imageSource.getImage();
        if (imageOrphanCheck != null) {
            ImageSource oldPrimarySourceOfImage = imageOrphanCheck.getPrimarySource();
            if (oldPrimarySourceOfImage != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Image " + imageOrphanCheck + " already has an item of type ImageSource whose image column cannot be null. Please make another selection for the image field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Image image = imageSource.getImage();
            if (image != null) {
                image = em.getReference(image.getClass(), image.getId());
                imageSource.setImage(image);
            }
            List<Tag> attachedTagList = new ArrayList<Tag>();
            for (Tag tagListTagToAttach : imageSource.getTagList()) {
                tagListTagToAttach = em.getReference(tagListTagToAttach.getClass(), tagListTagToAttach.getId());
                attachedTagList.add(tagListTagToAttach);
            }
            imageSource.setTagList(attachedTagList);
            List<Image> attachedImageList = new ArrayList<Image>();
            for (Image imageListImageToAttach : imageSource.getImageList()) {
                imageListImageToAttach = em.getReference(imageListImageToAttach.getClass(), imageListImageToAttach.getId());
                attachedImageList.add(imageListImageToAttach);
            }
            imageSource.setImageList(attachedImageList);
            em.persist(imageSource);
            if (image != null) {
                image.setPrimarySource(imageSource);
                image = em.merge(image);
            }
            for (Tag tagListTag : imageSource.getTagList()) {
                tagListTag.getImageSourceList().add(imageSource);
                tagListTag = em.merge(tagListTag);
            }
            for (Image imageListImage : imageSource.getImageList()) {
                ImageSource oldPrimarySourceOfImageListImage = imageListImage.getPrimarySource();
                imageListImage.setPrimarySource(imageSource);
                imageListImage = em.merge(imageListImage);
                if (oldPrimarySourceOfImageListImage != null) {
                    oldPrimarySourceOfImageListImage.getImageList().remove(imageListImage);
                    oldPrimarySourceOfImageListImage = em.merge(oldPrimarySourceOfImageListImage);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findImageSource(imageSource.getId()) != null) {
                throw new PreexistingEntityException("ImageSource " + imageSource + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ImageSource imageSource) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ImageSource persistentImageSource = em.find(ImageSource.class, imageSource.getId());
            Image imageOld = persistentImageSource.getImage();
            Image imageNew = imageSource.getImage();
            List<Tag> tagListOld = persistentImageSource.getTagList();
            List<Tag> tagListNew = imageSource.getTagList();
            List<Image> imageListOld = persistentImageSource.getImageList();
            List<Image> imageListNew = imageSource.getImageList();
            List<String> illegalOrphanMessages = null;
            if (imageNew != null && !imageNew.equals(imageOld)) {
                ImageSource oldPrimarySourceOfImage = imageNew.getPrimarySource();
                if (oldPrimarySourceOfImage != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Image " + imageNew + " already has an item of type ImageSource whose image column cannot be null. Please make another selection for the image field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (imageNew != null) {
                imageNew = em.getReference(imageNew.getClass(), imageNew.getId());
                imageSource.setImage(imageNew);
            }
            List<Tag> attachedTagListNew = new ArrayList<Tag>();
            for (Tag tagListNewTagToAttach : tagListNew) {
                tagListNewTagToAttach = em.getReference(tagListNewTagToAttach.getClass(), tagListNewTagToAttach.getId());
                attachedTagListNew.add(tagListNewTagToAttach);
            }
            tagListNew = attachedTagListNew;
            imageSource.setTagList(tagListNew);
            List<Image> attachedImageListNew = new ArrayList<Image>();
            for (Image imageListNewImageToAttach : imageListNew) {
                imageListNewImageToAttach = em.getReference(imageListNewImageToAttach.getClass(), imageListNewImageToAttach.getId());
                attachedImageListNew.add(imageListNewImageToAttach);
            }
            imageListNew = attachedImageListNew;
            imageSource.setImageList(imageListNew);
            imageSource = em.merge(imageSource);
            if (imageOld != null && !imageOld.equals(imageNew)) {
                imageOld.setPrimarySource(null);
                imageOld = em.merge(imageOld);
            }
            if (imageNew != null && !imageNew.equals(imageOld)) {
                imageNew.setPrimarySource(imageSource);
                imageNew = em.merge(imageNew);
            }
            for (Tag tagListOldTag : tagListOld) {
                if (!tagListNew.contains(tagListOldTag)) {
                    tagListOldTag.getImageSourceList().remove(imageSource);
                    tagListOldTag = em.merge(tagListOldTag);
                }
            }
            for (Tag tagListNewTag : tagListNew) {
                if (!tagListOld.contains(tagListNewTag)) {
                    tagListNewTag.getImageSourceList().add(imageSource);
                    tagListNewTag = em.merge(tagListNewTag);
                }
            }
            for (Image imageListOldImage : imageListOld) {
                if (!imageListNew.contains(imageListOldImage)) {
                    imageListOldImage.setPrimarySource(null);
                    imageListOldImage = em.merge(imageListOldImage);
                }
            }
            for (Image imageListNewImage : imageListNew) {
                if (!imageListOld.contains(imageListNewImage)) {
                    ImageSource oldPrimarySourceOfImageListNewImage = imageListNewImage.getPrimarySource();
                    imageListNewImage.setPrimarySource(imageSource);
                    imageListNewImage = em.merge(imageListNewImage);
                    if (oldPrimarySourceOfImageListNewImage != null && !oldPrimarySourceOfImageListNewImage.equals(imageSource)) {
                        oldPrimarySourceOfImageListNewImage.getImageList().remove(imageListNewImage);
                        oldPrimarySourceOfImageListNewImage = em.merge(oldPrimarySourceOfImageListNewImage);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = imageSource.getId();
                if (findImageSource(id) == null) {
                    throw new NonexistentEntityException("The imageSource with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ImageSource imageSource;
            try {
                imageSource = em.getReference(ImageSource.class, id);
                imageSource.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The imageSource with id " + id + " no longer exists.", enfe);
            }
            Image image = imageSource.getImage();
            if (image != null) {
                image.setPrimarySource(null);
                image = em.merge(image);
            }
            List<Tag> tagList = imageSource.getTagList();
            for (Tag tagListTag : tagList) {
                tagListTag.getImageSourceList().remove(imageSource);
                tagListTag = em.merge(tagListTag);
            }
            List<Image> imageList = imageSource.getImageList();
            for (Image imageListImage : imageList) {
                imageListImage.setPrimarySource(null);
                imageListImage = em.merge(imageListImage);
            }
            em.remove(imageSource);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ImageSource> findImageSourceEntities() {
        return findImageSourceEntities(true, -1, -1);
    }

    public List<ImageSource> findImageSourceEntities(int maxResults, int firstResult) {
        return findImageSourceEntities(false, maxResults, firstResult);
    }

    private List<ImageSource> findImageSourceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ImageSource.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ImageSource findImageSource(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ImageSource.class, id);
        } finally {
            em.close();
        }
    }

    public int getImageSourceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ImageSource> rt = cq.from(ImageSource.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
