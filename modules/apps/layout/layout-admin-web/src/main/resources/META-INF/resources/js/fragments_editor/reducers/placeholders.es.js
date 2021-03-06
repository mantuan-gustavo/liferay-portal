import {
	CLEAR_DRAG_TARGET,
	UPDATE_DRAG_TARGET,
	UPDATE_HIGHLIGHT_MAPPING_STATUS
} from '../actions/actions.es';

/**
 * Available drag positions
 * @review
 * @type {!object}
 */
const DRAG_POSITIONS = {
	bottom: 'drag-bottom',
	top: 'drag-top'
};

/**
 * Possible drop target types
 * @review
 * @type {!object}
 */
const DROP_TARGET_TYPES = {
	column: 'layout-column',
	fragment: 'fragment-entry-link',
	fragmentList: 'fragment-entry-link-list',
	section: 'layout-section'
};

/**
 * Updates hover status with the information sent.
 * @param {!object} state
 * @param {CLEAR_DRAG_TARGET|UPDATE_DRAG_TARGET} actionType
 * @param {!object} payload
 * @param {string} payload.hoveredElementBorder
 * @param {string} payload.hoveredElementId
 * @param {string} payload.hoveredElementType
 * @return {object}
 * @review
 */
function updateDragTargetReducer(state, actionType, payload) {
	const nextState = Object.assign({}, state);

	if (actionType === CLEAR_DRAG_TARGET) {
		nextState.hoveredElementBorder = null;
		nextState.hoveredElementId = null;
		nextState.hoveredElementType = null;
	}
	else if (actionType === UPDATE_DRAG_TARGET) {
		nextState.hoveredElementBorder = payload.hoveredElementBorder;
		nextState.hoveredElementId = payload.hoveredElementId;
		nextState.hoveredElementType = payload.hoveredElementType;
	}

	return nextState;
}

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {object} payload
 * @param {bool} payload.highlightMapping
 * @return {object}
 * @review
 */
function updateHighlightMappingReducer(state, actionType, payload) {
	let nextState = state;

	if (actionType === UPDATE_HIGHLIGHT_MAPPING_STATUS) {
		nextState = Object.assign(
			{},
			nextState,
			{
				highlightMapping: Boolean(payload.highlightMapping)
			}
		);
	}

	return nextState;
}

export {
	DRAG_POSITIONS,
	DROP_TARGET_TYPES,
	updateDragTargetReducer,
	updateHighlightMappingReducer
};