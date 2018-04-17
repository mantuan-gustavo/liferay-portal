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

package com.liferay.dynamic.data.mapping.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.kernel.service.persistence.AssetEntryPersistence;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstanceFinder;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstancePersistence;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstanceRecordFinder;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstanceRecordPersistence;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstanceRecordVersionPersistence;

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
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.service.persistence.WorkflowInstanceLinkPersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the ddm form instance record local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceRecordLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceRecordLocalServiceImpl
 * @see com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class DDMFormInstanceRecordLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements DDMFormInstanceRecordLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalServiceUtil} to access the ddm form instance record local service.
	 */

	/**
	 * Adds the ddm form instance record to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmFormInstanceRecord the ddm form instance record
	 * @return the ddm form instance record that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDMFormInstanceRecord addDDMFormInstanceRecord(
		DDMFormInstanceRecord ddmFormInstanceRecord) {
		ddmFormInstanceRecord.setNew(true);

		return ddmFormInstanceRecordPersistence.update(ddmFormInstanceRecord);
	}

	/**
	 * Creates a new ddm form instance record with the primary key. Does not add the ddm form instance record to the database.
	 *
	 * @param formInstanceRecordId the primary key for the new ddm form instance record
	 * @return the new ddm form instance record
	 */
	@Override
	@Transactional(enabled = false)
	public DDMFormInstanceRecord createDDMFormInstanceRecord(
		long formInstanceRecordId) {
		return ddmFormInstanceRecordPersistence.create(formInstanceRecordId);
	}

	/**
	 * Deletes the ddm form instance record with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formInstanceRecordId the primary key of the ddm form instance record
	 * @return the ddm form instance record that was removed
	 * @throws PortalException if a ddm form instance record with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DDMFormInstanceRecord deleteDDMFormInstanceRecord(
		long formInstanceRecordId) throws PortalException {
		return ddmFormInstanceRecordPersistence.remove(formInstanceRecordId);
	}

	/**
	 * Deletes the ddm form instance record from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmFormInstanceRecord the ddm form instance record
	 * @return the ddm form instance record that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DDMFormInstanceRecord deleteDDMFormInstanceRecord(
		DDMFormInstanceRecord ddmFormInstanceRecord) {
		return ddmFormInstanceRecordPersistence.remove(ddmFormInstanceRecord);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(DDMFormInstanceRecord.class,
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
		return ddmFormInstanceRecordPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return ddmFormInstanceRecordPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return ddmFormInstanceRecordPersistence.findWithDynamicQuery(dynamicQuery,
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
		return ddmFormInstanceRecordPersistence.countWithDynamicQuery(dynamicQuery);
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
		return ddmFormInstanceRecordPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public DDMFormInstanceRecord fetchDDMFormInstanceRecord(
		long formInstanceRecordId) {
		return ddmFormInstanceRecordPersistence.fetchByPrimaryKey(formInstanceRecordId);
	}

	/**
	 * Returns the ddm form instance record matching the UUID and group.
	 *
	 * @param uuid the ddm form instance record's UUID
	 * @param groupId the primary key of the group
	 * @return the matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord fetchDDMFormInstanceRecordByUuidAndGroupId(
		String uuid, long groupId) {
		return ddmFormInstanceRecordPersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the ddm form instance record with the primary key.
	 *
	 * @param formInstanceRecordId the primary key of the ddm form instance record
	 * @return the ddm form instance record
	 * @throws PortalException if a ddm form instance record with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecord getDDMFormInstanceRecord(
		long formInstanceRecordId) throws PortalException {
		return ddmFormInstanceRecordPersistence.findByPrimaryKey(formInstanceRecordId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(ddmFormInstanceRecordLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(DDMFormInstanceRecord.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("formInstanceRecordId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(ddmFormInstanceRecordLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(DDMFormInstanceRecord.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"formInstanceRecordId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(ddmFormInstanceRecordLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(DDMFormInstanceRecord.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("formInstanceRecordId");
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

		exportActionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<DDMFormInstanceRecord>() {
				@Override
				public void performAction(
					DDMFormInstanceRecord ddmFormInstanceRecord)
					throws PortalException {
					StagedModelDataHandlerUtil.exportStagedModel(portletDataContext,
						ddmFormInstanceRecord);
				}
			});
		exportActionableDynamicQuery.setStagedModelType(new StagedModelType(
				PortalUtil.getClassNameId(DDMFormInstanceRecord.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return ddmFormInstanceRecordLocalService.deleteDDMFormInstanceRecord((DDMFormInstanceRecord)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return ddmFormInstanceRecordPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the ddm form instance records matching the UUID and company.
	 *
	 * @param uuid the UUID of the ddm form instance records
	 * @param companyId the primary key of the company
	 * @return the matching ddm form instance records, or an empty list if no matches were found
	 */
	@Override
	public List<DDMFormInstanceRecord> getDDMFormInstanceRecordsByUuidAndCompanyId(
		String uuid, long companyId) {
		return ddmFormInstanceRecordPersistence.findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of ddm form instance records matching the UUID and company.
	 *
	 * @param uuid the UUID of the ddm form instance records
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching ddm form instance records, or an empty list if no matches were found
	 */
	@Override
	public List<DDMFormInstanceRecord> getDDMFormInstanceRecordsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {
		return ddmFormInstanceRecordPersistence.findByUuid_C(uuid, companyId,
			start, end, orderByComparator);
	}

	/**
	 * Returns the ddm form instance record matching the UUID and group.
	 *
	 * @param uuid the ddm form instance record's UUID
	 * @param groupId the primary key of the group
	 * @return the matching ddm form instance record
	 * @throws PortalException if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord getDDMFormInstanceRecordByUuidAndGroupId(
		String uuid, long groupId) throws PortalException {
		return ddmFormInstanceRecordPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the ddm form instance records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @return the range of ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> getDDMFormInstanceRecords(int start,
		int end) {
		return ddmFormInstanceRecordPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of ddm form instance records.
	 *
	 * @return the number of ddm form instance records
	 */
	@Override
	public int getDDMFormInstanceRecordsCount() {
		return ddmFormInstanceRecordPersistence.countAll();
	}

	/**
	 * Updates the ddm form instance record in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ddmFormInstanceRecord the ddm form instance record
	 * @return the ddm form instance record that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDMFormInstanceRecord updateDDMFormInstanceRecord(
		DDMFormInstanceRecord ddmFormInstanceRecord) {
		return ddmFormInstanceRecordPersistence.update(ddmFormInstanceRecord);
	}

	/**
	 * Returns the ddm form instance record local service.
	 *
	 * @return the ddm form instance record local service
	 */
	public DDMFormInstanceRecordLocalService getDDMFormInstanceRecordLocalService() {
		return ddmFormInstanceRecordLocalService;
	}

	/**
	 * Sets the ddm form instance record local service.
	 *
	 * @param ddmFormInstanceRecordLocalService the ddm form instance record local service
	 */
	public void setDDMFormInstanceRecordLocalService(
		DDMFormInstanceRecordLocalService ddmFormInstanceRecordLocalService) {
		this.ddmFormInstanceRecordLocalService = ddmFormInstanceRecordLocalService;
	}

	/**
	 * Returns the ddm form instance record persistence.
	 *
	 * @return the ddm form instance record persistence
	 */
	public DDMFormInstanceRecordPersistence getDDMFormInstanceRecordPersistence() {
		return ddmFormInstanceRecordPersistence;
	}

	/**
	 * Sets the ddm form instance record persistence.
	 *
	 * @param ddmFormInstanceRecordPersistence the ddm form instance record persistence
	 */
	public void setDDMFormInstanceRecordPersistence(
		DDMFormInstanceRecordPersistence ddmFormInstanceRecordPersistence) {
		this.ddmFormInstanceRecordPersistence = ddmFormInstanceRecordPersistence;
	}

	/**
	 * Returns the ddm form instance record finder.
	 *
	 * @return the ddm form instance record finder
	 */
	public DDMFormInstanceRecordFinder getDDMFormInstanceRecordFinder() {
		return ddmFormInstanceRecordFinder;
	}

	/**
	 * Sets the ddm form instance record finder.
	 *
	 * @param ddmFormInstanceRecordFinder the ddm form instance record finder
	 */
	public void setDDMFormInstanceRecordFinder(
		DDMFormInstanceRecordFinder ddmFormInstanceRecordFinder) {
		this.ddmFormInstanceRecordFinder = ddmFormInstanceRecordFinder;
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
	 * Returns the ddm form instance local service.
	 *
	 * @return the ddm form instance local service
	 */
	public com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService getDDMFormInstanceLocalService() {
		return ddmFormInstanceLocalService;
	}

	/**
	 * Sets the ddm form instance local service.
	 *
	 * @param ddmFormInstanceLocalService the ddm form instance local service
	 */
	public void setDDMFormInstanceLocalService(
		com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService ddmFormInstanceLocalService) {
		this.ddmFormInstanceLocalService = ddmFormInstanceLocalService;
	}

	/**
	 * Returns the ddm form instance persistence.
	 *
	 * @return the ddm form instance persistence
	 */
	public DDMFormInstancePersistence getDDMFormInstancePersistence() {
		return ddmFormInstancePersistence;
	}

	/**
	 * Sets the ddm form instance persistence.
	 *
	 * @param ddmFormInstancePersistence the ddm form instance persistence
	 */
	public void setDDMFormInstancePersistence(
		DDMFormInstancePersistence ddmFormInstancePersistence) {
		this.ddmFormInstancePersistence = ddmFormInstancePersistence;
	}

	/**
	 * Returns the ddm form instance finder.
	 *
	 * @return the ddm form instance finder
	 */
	public DDMFormInstanceFinder getDDMFormInstanceFinder() {
		return ddmFormInstanceFinder;
	}

	/**
	 * Sets the ddm form instance finder.
	 *
	 * @param ddmFormInstanceFinder the ddm form instance finder
	 */
	public void setDDMFormInstanceFinder(
		DDMFormInstanceFinder ddmFormInstanceFinder) {
		this.ddmFormInstanceFinder = ddmFormInstanceFinder;
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
	 * Returns the workflow instance link local service.
	 *
	 * @return the workflow instance link local service
	 */
	public com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService getWorkflowInstanceLinkLocalService() {
		return workflowInstanceLinkLocalService;
	}

	/**
	 * Sets the workflow instance link local service.
	 *
	 * @param workflowInstanceLinkLocalService the workflow instance link local service
	 */
	public void setWorkflowInstanceLinkLocalService(
		com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService) {
		this.workflowInstanceLinkLocalService = workflowInstanceLinkLocalService;
	}

	/**
	 * Returns the workflow instance link persistence.
	 *
	 * @return the workflow instance link persistence
	 */
	public WorkflowInstanceLinkPersistence getWorkflowInstanceLinkPersistence() {
		return workflowInstanceLinkPersistence;
	}

	/**
	 * Sets the workflow instance link persistence.
	 *
	 * @param workflowInstanceLinkPersistence the workflow instance link persistence
	 */
	public void setWorkflowInstanceLinkPersistence(
		WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence) {
		this.workflowInstanceLinkPersistence = workflowInstanceLinkPersistence;
	}

	/**
	 * Returns the asset entry local service.
	 *
	 * @return the asset entry local service
	 */
	public com.liferay.asset.kernel.service.AssetEntryLocalService getAssetEntryLocalService() {
		return assetEntryLocalService;
	}

	/**
	 * Sets the asset entry local service.
	 *
	 * @param assetEntryLocalService the asset entry local service
	 */
	public void setAssetEntryLocalService(
		com.liferay.asset.kernel.service.AssetEntryLocalService assetEntryLocalService) {
		this.assetEntryLocalService = assetEntryLocalService;
	}

	/**
	 * Returns the asset entry persistence.
	 *
	 * @return the asset entry persistence
	 */
	public AssetEntryPersistence getAssetEntryPersistence() {
		return assetEntryPersistence;
	}

	/**
	 * Sets the asset entry persistence.
	 *
	 * @param assetEntryPersistence the asset entry persistence
	 */
	public void setAssetEntryPersistence(
		AssetEntryPersistence assetEntryPersistence) {
		this.assetEntryPersistence = assetEntryPersistence;
	}

	/**
	 * Returns the ddm form instance record version local service.
	 *
	 * @return the ddm form instance record version local service
	 */
	public com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService getDDMFormInstanceRecordVersionLocalService() {
		return ddmFormInstanceRecordVersionLocalService;
	}

	/**
	 * Sets the ddm form instance record version local service.
	 *
	 * @param ddmFormInstanceRecordVersionLocalService the ddm form instance record version local service
	 */
	public void setDDMFormInstanceRecordVersionLocalService(
		com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService ddmFormInstanceRecordVersionLocalService) {
		this.ddmFormInstanceRecordVersionLocalService = ddmFormInstanceRecordVersionLocalService;
	}

	/**
	 * Returns the ddm form instance record version persistence.
	 *
	 * @return the ddm form instance record version persistence
	 */
	public DDMFormInstanceRecordVersionPersistence getDDMFormInstanceRecordVersionPersistence() {
		return ddmFormInstanceRecordVersionPersistence;
	}

	/**
	 * Sets the ddm form instance record version persistence.
	 *
	 * @param ddmFormInstanceRecordVersionPersistence the ddm form instance record version persistence
	 */
	public void setDDMFormInstanceRecordVersionPersistence(
		DDMFormInstanceRecordVersionPersistence ddmFormInstanceRecordVersionPersistence) {
		this.ddmFormInstanceRecordVersionPersistence = ddmFormInstanceRecordVersionPersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord",
			ddmFormInstanceRecordLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return DDMFormInstanceRecordLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return DDMFormInstanceRecord.class;
	}

	protected String getModelClassName() {
		return DDMFormInstanceRecord.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = ddmFormInstanceRecordPersistence.getDataSource();

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

	@BeanReference(type = DDMFormInstanceRecordLocalService.class)
	protected DDMFormInstanceRecordLocalService ddmFormInstanceRecordLocalService;
	@BeanReference(type = DDMFormInstanceRecordPersistence.class)
	protected DDMFormInstanceRecordPersistence ddmFormInstanceRecordPersistence;
	@BeanReference(type = DDMFormInstanceRecordFinder.class)
	protected DDMFormInstanceRecordFinder ddmFormInstanceRecordFinder;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService.class)
	protected com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService ddmFormInstanceLocalService;
	@BeanReference(type = DDMFormInstancePersistence.class)
	protected DDMFormInstancePersistence ddmFormInstancePersistence;
	@BeanReference(type = DDMFormInstanceFinder.class)
	protected DDMFormInstanceFinder ddmFormInstanceFinder;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService.class)
	protected com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService;
	@ServiceReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	@ServiceReference(type = com.liferay.asset.kernel.service.AssetEntryLocalService.class)
	protected com.liferay.asset.kernel.service.AssetEntryLocalService assetEntryLocalService;
	@ServiceReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService.class)
	protected com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService ddmFormInstanceRecordVersionLocalService;
	@BeanReference(type = DDMFormInstanceRecordVersionPersistence.class)
	protected DDMFormInstanceRecordVersionPersistence ddmFormInstanceRecordVersionPersistence;
	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}