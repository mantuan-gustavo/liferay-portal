/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.LayoutFriendlyURLPersistence;
import com.liferay.portal.kernel.service.persistence.UserFinder;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the layout friendly url local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portal.service.impl.LayoutFriendlyURLLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.impl.LayoutFriendlyURLLocalServiceImpl
 * @see com.liferay.portal.kernel.service.LayoutFriendlyURLLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class LayoutFriendlyURLLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements LayoutFriendlyURLLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portal.kernel.service.LayoutFriendlyURLLocalServiceUtil} to access the layout friendly url local service.
	 */

	/**
	 * Adds the layout friendly url to the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutFriendlyURL the layout friendly url
	 * @return the layout friendly url that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public LayoutFriendlyURL addLayoutFriendlyURL(
		LayoutFriendlyURL layoutFriendlyURL) {
		layoutFriendlyURL.setNew(true);

		return layoutFriendlyURLPersistence.update(layoutFriendlyURL);
	}

	/**
	 * Creates a new layout friendly url with the primary key. Does not add the layout friendly url to the database.
	 *
	 * @param layoutFriendlyURLId the primary key for the new layout friendly url
	 * @return the new layout friendly url
	 */
	@Override
	@Transactional(enabled = false)
	public LayoutFriendlyURL createLayoutFriendlyURL(long layoutFriendlyURLId) {
		return layoutFriendlyURLPersistence.create(layoutFriendlyURLId);
	}

	/**
	 * Deletes the layout friendly url with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutFriendlyURLId the primary key of the layout friendly url
	 * @return the layout friendly url that was removed
	 * @throws PortalException if a layout friendly url with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public LayoutFriendlyURL deleteLayoutFriendlyURL(long layoutFriendlyURLId)
		throws PortalException {
		return layoutFriendlyURLPersistence.remove(layoutFriendlyURLId);
	}

	/**
	 * Deletes the layout friendly url from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutFriendlyURL the layout friendly url
	 * @return the layout friendly url that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public LayoutFriendlyURL deleteLayoutFriendlyURL(
		LayoutFriendlyURL layoutFriendlyURL) {
		return layoutFriendlyURLPersistence.remove(layoutFriendlyURL);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(LayoutFriendlyURL.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return layoutFriendlyURLPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) {
		return layoutFriendlyURLPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator) {
		return layoutFriendlyURLPersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return layoutFriendlyURLPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return layoutFriendlyURLPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public LayoutFriendlyURL fetchLayoutFriendlyURL(long layoutFriendlyURLId) {
		return layoutFriendlyURLPersistence.fetchByPrimaryKey(layoutFriendlyURLId);
	}

	/**
	 * Returns the layout friendly url matching the UUID and group.
	 *
	 * @param uuid the layout friendly url's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout friendly url, or <code>null</code> if a matching layout friendly url could not be found
	 */
	@Override
	public LayoutFriendlyURL fetchLayoutFriendlyURLByUuidAndGroupId(
		String uuid, long groupId) {
		return layoutFriendlyURLPersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the layout friendly url with the primary key.
	 *
	 * @param layoutFriendlyURLId the primary key of the layout friendly url
	 * @return the layout friendly url
	 * @throws PortalException if a layout friendly url with the primary key could not be found
	 */
	@Override
	public LayoutFriendlyURL getLayoutFriendlyURL(long layoutFriendlyURLId)
		throws PortalException {
		return layoutFriendlyURLPersistence.findByPrimaryKey(layoutFriendlyURLId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(layoutFriendlyURLLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(LayoutFriendlyURL.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("layoutFriendlyURLId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(layoutFriendlyURLLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(LayoutFriendlyURL.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"layoutFriendlyURLId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(layoutFriendlyURLLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(LayoutFriendlyURL.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("layoutFriendlyURLId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {
		final ExportActionableDynamicQuery exportActionableDynamicQuery = new ExportActionableDynamicQuery() {
				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary = portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(stagedModelType,
						modelAdditionCount);

					long modelDeletionCount = ExportImportHelperUtil.getModelDeletionCount(portletDataContext,
							stagedModelType);

					manifestSummary.addModelDeletionCount(stagedModelType,
						modelDeletionCount);

					return modelAdditionCount;
				}
			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(new ActionableDynamicQuery.AddCriteriaMethod() {
				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(dynamicQuery,
						"modifiedDate");
				}
			});

		exportActionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setGroupId(portletDataContext.getScopeGroupId());

		exportActionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<LayoutFriendlyURL>() {
				@Override
				public void performAction(LayoutFriendlyURL layoutFriendlyURL)
					throws PortalException {
					StagedModelDataHandlerUtil.exportStagedModel(portletDataContext,
						layoutFriendlyURL);
				}
			});
		exportActionableDynamicQuery.setStagedModelType(new StagedModelType(
				PortalUtil.getClassNameId(LayoutFriendlyURL.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return layoutFriendlyURLLocalService.deleteLayoutFriendlyURL((LayoutFriendlyURL)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return layoutFriendlyURLPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the layout friendly urls matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout friendly urls
	 * @param companyId the primary key of the company
	 * @return the matching layout friendly urls, or an empty list if no matches were found
	 */
	@Override
	public List<LayoutFriendlyURL> getLayoutFriendlyURLsByUuidAndCompanyId(
		String uuid, long companyId) {
		return layoutFriendlyURLPersistence.findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of layout friendly urls matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout friendly urls
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of layout friendly urls
	 * @param end the upper bound of the range of layout friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching layout friendly urls, or an empty list if no matches were found
	 */
	@Override
	public List<LayoutFriendlyURL> getLayoutFriendlyURLsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutFriendlyURL> orderByComparator) {
		return layoutFriendlyURLPersistence.findByUuid_C(uuid, companyId,
			start, end, orderByComparator);
	}

	/**
	 * Returns the layout friendly url matching the UUID and group.
	 *
	 * @param uuid the layout friendly url's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout friendly url
	 * @throws PortalException if a matching layout friendly url could not be found
	 */
	@Override
	public LayoutFriendlyURL getLayoutFriendlyURLByUuidAndGroupId(String uuid,
		long groupId) throws PortalException {
		return layoutFriendlyURLPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the layout friendly urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout friendly urls
	 * @param end the upper bound of the range of layout friendly urls (not inclusive)
	 * @return the range of layout friendly urls
	 */
	@Override
	public List<LayoutFriendlyURL> getLayoutFriendlyURLs(int start, int end) {
		return layoutFriendlyURLPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of layout friendly urls.
	 *
	 * @return the number of layout friendly urls
	 */
	@Override
	public int getLayoutFriendlyURLsCount() {
		return layoutFriendlyURLPersistence.countAll();
	}

	/**
	 * Updates the layout friendly url in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param layoutFriendlyURL the layout friendly url
	 * @return the layout friendly url that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public LayoutFriendlyURL updateLayoutFriendlyURL(
		LayoutFriendlyURL layoutFriendlyURL) {
		return layoutFriendlyURLPersistence.update(layoutFriendlyURL);
	}

	/**
	 * Returns the layout friendly url local service.
	 *
	 * @return the layout friendly url local service
	 */
	public LayoutFriendlyURLLocalService getLayoutFriendlyURLLocalService() {
		return layoutFriendlyURLLocalService;
	}

	/**
	 * Sets the layout friendly url local service.
	 *
	 * @param layoutFriendlyURLLocalService the layout friendly url local service
	 */
	public void setLayoutFriendlyURLLocalService(
		LayoutFriendlyURLLocalService layoutFriendlyURLLocalService) {
		this.layoutFriendlyURLLocalService = layoutFriendlyURLLocalService;
	}

	/**
	 * Returns the layout friendly url persistence.
	 *
	 * @return the layout friendly url persistence
	 */
	public LayoutFriendlyURLPersistence getLayoutFriendlyURLPersistence() {
		return layoutFriendlyURLPersistence;
	}

	/**
	 * Sets the layout friendly url persistence.
	 *
	 * @param layoutFriendlyURLPersistence the layout friendly url persistence
	 */
	public void setLayoutFriendlyURLPersistence(
		LayoutFriendlyURLPersistence layoutFriendlyURLPersistence) {
		this.layoutFriendlyURLPersistence = layoutFriendlyURLPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	/**
	 * Returns the user finder.
	 *
	 * @return the user finder
	 */
	public UserFinder getUserFinder() {
		return userFinder;
	}

	/**
	 * Sets the user finder.
	 *
	 * @param userFinder the user finder
	 */
	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.portal.kernel.model.LayoutFriendlyURL",
			layoutFriendlyURLLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.portal.kernel.model.LayoutFriendlyURL");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return LayoutFriendlyURLLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return LayoutFriendlyURL.class;
	}

	protected String getModelClassName() {
		return LayoutFriendlyURL.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = layoutFriendlyURLPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = LayoutFriendlyURLLocalService.class)
	protected LayoutFriendlyURLLocalService layoutFriendlyURLLocalService;
	@BeanReference(type = LayoutFriendlyURLPersistence.class)
	protected LayoutFriendlyURLPersistence layoutFriendlyURLPersistence;
	@BeanReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserFinder.class)
	protected UserFinder userFinder;
	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}