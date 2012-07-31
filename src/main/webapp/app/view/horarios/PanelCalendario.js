Ext.define('HOR.view.horarios.PanelCalendario',
{
    extend : 'Extensible.calendar.CalendarPanel',
    alias : 'widget.panelCalendario',
    region : 'center',
    title : 'Calendari',
    depends : [ 'HOR.store.StoreCalendarios', 'HOR.store.StoreEventos' ],
    calendarStore : Ext.create('HOR.store.StoreCalendarios'),
    eventStore : Ext.create('HOR.store.StoreEventos'),
    editModal : true,
    flex : 1,
    padding : 5,
    activeItem : 1,
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

    initComponent : function()
    {
        this.callParent(arguments);
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
    }
});