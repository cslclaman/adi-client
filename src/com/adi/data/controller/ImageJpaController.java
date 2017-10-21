/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.data.controller;

import com.adi.data.controller.exceptions.IllegalOrphanException;
import com.adi.data.controller.exceptions.NonexistentEntityException;
import com.adi.data.controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.adi.data.entity.ImageSource;
import com.adi.data.entity.AdiTag;
import com.adi.data.entity.Image;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Caique
 */
public class ImageJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    public ImageJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Image image) throws PreexistingEntityException, Exception {
        if (image.getAdiTagList() == null) {
            image.setAdiTagList(new ArrayList<>());
        }
        if (image.getImageSourceList() == null) {
            image.setImageSourceList(new ArrayList<>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ImageSource primarySource = image.getPrimarySource();
            if (primarySource != null) {
                primarySource = em.getReference(primarySource.getClass(), primarySource.getId());
                image.setPrimarySource(primarySource);
            }
            List<AdiTag> attachedAdiTagList = new ArrayList<>();
            for (AdiTag adiTagListAdiTagToAttach : image.getAdiTagList()) {
                adiTagListAdiTagToAttach = em.getReference(adiTagListAdiTagToAttach.getClass(), adiTagListAdiTagToAttach.getId());
                attachedAdiTagList.add(adiTagListAdiTagToAttach);
            }
            image.setAdiTagList(attachedAdiTagList);
            List<ImageSource> attachedImageSourceList = new ArrayList<>();
            for (ImageSource imageSourceListImageSourceToAttach : image.getImageSourceList()) {
                imageSourceListImageSourceToAttach = em.getReference(imageSourceListImageSourceToAttach.getClass(), imageSourceListImageSourceToAttach.getId());
                attachedImageSourceList.add(imageSourceListImageSourceToAttach);
            }
            image.setImageSourceList(attachedImageSourceList);
            em.persist(image);
            if (primarySource != null) {
                primarySource.getImageList().add(image);
                em.merge(primarySource);
            }
            for (AdiTag adiTagListAdiTag : image.getAdiTagList()) {
                adiTagListAdiTag.getImageList().add(image);
                em.merge(adiTagListAdiTag);
            }
            for (ImageSource imageSourceListImageSource : image.getImageSourceList()) {
                Image oldImageOfImageSourceListImageSource = imageSourceListImageSource.getImage();
                imageSourceListImageSource.setImage(image);
                imageSourceListImageSource = em.merge(imageSourceListImageSource);
                if (oldImageOfImageSourceListImageSource != null) {
                    oldImageOfImageSourceListImageSource.getImageSourceList().remove(imageSourceListImageSource);
                    em.merge(oldImageOfImageSourceListImageSource);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findImage(image.getId()) != null) {
                throw new PreexistingEntityException("Image " + image + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Image image) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Image persistentImage = em.find(Image.class, image.getId());
            ImageSource primarySourceOld = persistentImage.getPrimarySource();
            ImageSource primarySourceNew = image.getPrimarySource();
            List<AdiTag> adiTagListOld = persistentImage.getAdiTagList();
            List<AdiTag> adiTagListNew = image.getAdiTagList();
            List<ImageSource> imageSourceListOld = persistentImage.getImageSourceList();
            List<ImageSource> imageSourceListNew = image.getImageSourceList();
            List<String> illegalOrphanMessages = null;
            for (ImageSource imageSourceListOldImageSource : imageSourceListOld) {
                if (!imageSourceListNew.contains(imageSourceListOldImageSource)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ImageSource " + imageSourceListOldImageSource + " since its image field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (primarySourceNew != null) {
                primarySourceNew = em.getReference(primarySourceNew.getClass(), primarySourceNew.getId());
                image.setPrimarySource(primarySourceNew);
            }
            List<AdiTag> attachedAdiTagListNew = new ArrayList<AdiTag>();
            for (AdiTag adiTagListNewAdiTagToAttach : adiTagListNew) {
                adiTagListNewAdiTagToAttach = em.getReference(adiTagListNewAdiTagToAttach.getClass(), adiTagListNewAdiTagToAttach.getId());
                attachedAdiTagListNew.add(adiTagListNewAdiTagToAttach);
            }
            adiTagListNew = attachedAdiTagListNew;
            image.setAdiTagList(adiTagListNew);
            List<ImageSource> attachedImageSourceListNew = new ArrayList<ImageSource>();
            for (ImageSource imageSourceListNewImageSourceToAttach : imageSourceListNew) {
                imageSourceListNewImageSourceToAttach = em.getReference(imageSourceListNewImageSourceToAttach.getClass(), imageSourceListNewImageSourceToAttach.getId());
                attachedImageSourceListNew.add(imageSourceListNewImageSourceToAttach);
            }
            imageSourceListNew = attachedImageSourceListNew;
            image.setImageSourceList(imageSourceListNew);
            image = em.merge(image);
            if (primarySourceOld != null && !primarySourceOld.equals(primarySourceNew)) {
                primarySourceOld.getImageList().remove(image);
                primarySourceOld = em.merge(primarySourceOld);
            }
            if (primarySourceNew != null && !primarySourceNew.equals(primarySourceOld)) {
                primarySourceNew.getImageList().add(image);
                primarySourceNew = em.merge(primarySourceNew);
            }
            for (AdiTag adiTagListOldAdiTag : adiTagListOld) {
                if (!adiTagListNew.contains(adiTagListOldAdiTag)) {
                    adiTagListOldAdiTag.getImageList().remove(image);
                    adiTagListOldAdiTag = em.merge(adiTagListOldAdiTag);
                }
            }
            for (AdiTag adiTagListNewAdiTag : adiTagListNew) {
                if (!adiTagListOld.contains(adiTagListNewAdiTag)) {
                    adiTagListNewAdiTag.getImageList().add(image);
                    adiTagListNewAdiTag = em.merge(adiTagListNewAdiTag);
                }
            }
            for (ImageSource imageSourceListNewImageSource : imageSourceListNew) {
                if (!imageSourceListOld.contains(imageSourceListNewImageSource)) {
                    Image oldImageOfImageSourceListNewImageSource = imageSourceListNewImageSource.getImage();
                    imageSourceListNewImageSource.setImage(image);
                    imageSourceListNewImageSource = em.merge(imageSourceListNewImageSource);
                    if (oldImageOfImageSourceListNewImageSource != null && !oldImageOfImageSourceListNewImageSource.equals(image)) {
                        oldImageOfImageSourceListNewImageSource.getImageSourceList().remove(imageSourceListNewImageSource);
                        oldImageOfImageSourceListNewImageSource = em.merge(oldImageOfImageSourceListNewImageSource);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = image.getId();
                if (findImage(id) == null) {
                    throw new NonexistentEntityException("The image with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Image image;
            try {
                image = em.getReference(Image.class, id);
                image.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The image with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ImageSource> imageSourceListOrphanCheck = image.getImageSourceList();
            for (ImageSource imageSourceListOrphanCheckImageSource : imageSourceListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Image (" + image + ") cannot be destroyed since the ImageSource " + imageSourceListOrphanCheckImageSource + " in its imageSourceList field has a non-nullable image field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            ImageSource primarySource = image.getPrimarySource();
            if (primarySource != null) {
                primarySource.getImageList().remove(image);
                primarySource = em.merge(primarySource);
            }
            List<AdiTag> adiTagList = image.getAdiTagList();
            for (AdiTag adiTagListAdiTag : adiTagList) {
                adiTagListAdiTag.getImageList().remove(image);
                adiTagListAdiTag = em.merge(adiTagListAdiTag);
            }
            em.remove(image);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Image> findImageEntities() {
        return findImageEntities(true, -1, -1);
    }

    public List<Image> findImageEntities(int maxResults, int firstResult) {
        return findImageEntities(false, maxResults, firstResult);
    }

    private List<Image> findImageEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Image.class));
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

    public Image findImage(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Image.class, id);
        } finally {
            em.close();
        }
    }

    public int getImageCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Image> rt = cq.from(Image.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
