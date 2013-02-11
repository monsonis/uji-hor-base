Ext.define('HOR.view.horarios.PanelCalendario',
{
    extend : 'Extensible.calendar.CalendarPanel',
    alias : 'widget.panelCalendario',
    region : 'center',
    title : 'Calendari',
    depends : [ 'HOR.store.StoreCalendarios', 'HOR.store.StoreEventos' ],
    requires : [ 'HOR.view.aulas.asignacion.FormAsignacionAulas' ],
    calendarStore : Ext.create('HOR.store.StoreCalendarios'),
    eventStore : Ext.create('HOR.store.StoreEventos'),
    editModal : true,
    flex : 1,
    padding : 5,
    showTodayText : false,
    showNavToday : false,
    showDayView : false,
    showWeekView : false,
    showMonthView : false,
    weekViewCfg :
    {
        dayCount : 5,
        startDay : 1,
        startDayIsStatic : true,
    },
    showMultiDayView : true,
    showMultiWeekView : false,
    showNavJump : false,
    showNavNextPrev : false,
    multiDayViewCfg :
    {
        dayCount : 5,
        startDay : 1,
        startDayIsStatic : true,
        showTime : false,
        showMonth : false,
        getStoreParams : function()
        {
            var params = this.getStoreDateParams();
            params.estudioId = this.store.getProxy().extraParams['estudioId'];
            params.cursoId = this.store.getProxy().extraParams['cursoId'];
            params.grupoId = this.store.getProxy().extraParams['grupoId'];
            params.semestreId = this.store.getProxy().extraParams['semestreId'];
            return params;
        }
    },
    getMultiDayText : function()
    {
        return 'Setmana genèrica';
    },

    limpiaCalendario : function()
    {
        this.store.removeAll(false);
    },

    initComponent : function()
    {
        Extensible.calendar.template.BoxLayout.override(
        {
            firstWeekDateFormat : 'l',
            multiDayFirstDayFormat : 'l',
            multiDayMonthStartFormat : 'l'
        });

        this.callParent(arguments);

        this.add([
        {
            xtype : 'formAsignacionAulas',
            id: this.id+'-aula',
        } ]);
    },
    onStoreUpdate : function()
    {
    },
    
    showAsignarAulaView : function()
    {
    	var asignarAulaId = this.id + '-aula';    	
    	this.preAsignarAulaView = this.layout.getActiveItem().id;
    	this.setActiveViewForAsignarAula(asignarAulaId);
    	return this;
    },
    
    hideAsignarAulaView : function()
    {
    	if(this.preAsignarAulaView){
            this.setActiveViewForAsignarAula(this.preAsignarAulaView);
            delete this.preEditView;
        }
        return this;
    },
    
    setActiveViewForAsignarAula: function(id, startDate){
        var me = this,
            layout = me.layout,
            asignarAulaViewId = me.id + '-aula',
            toolbar;
        
        if (startDate) {
            me.startDate = startDate;
        }
        
        // Make sure we're actually changing views
        if (id !== layout.getActiveItem().id) {
            // Show/hide the toolbar first so that the layout will calculate the correct item size
            toolbar = me.getDockedItems('toolbar')[0];
            if (toolbar) {
                toolbar[id === asignarAulaViewId ? 'hide' : 'show']();
            }
            
            // Activate the new view and refresh the layout
            layout.setActiveItem(id || me.activeItem);
            me.doComponentLayout();
            me.activeView = layout.getActiveItem();
            
            if (id !== asignarAulaViewId) {
                if (id && id !== me.preAsignarAulaView) {
                    // We're changing to a different view, so the view dates are likely different.
                    // Re-set the start date so that the view range will be updated if needed.
                    // If id is undefined, it means this is the initial pass after render so we can
                    // skip this (as we don't want to cause a duplicate forced reload).
                    layout.activeItem.setStartDate(me.startDate, true);
                }
                // Switching to a view that's not the edit view (i.e., the nav bar will be visible)
                // so update the nav bar's selected view button
                me.updateNavState();
            }
            // Notify any listeners that the view changed
            me.fireViewChange();
        }
    },
    
    getEventStore : function() {
        return this.store;
    }
});