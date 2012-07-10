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
    activeItem: 1,
    showTodayText: false,
    weekViewCfg :
    {
        dayCount : 5,
        startDay : 1,
        startDayIsStatic : true,
        viewStartHour : 8,
        viewEndHour : 22,
    // hourHeight: 84
    },
    dayViewCfg :
    {
        viewStartHour : 8,
        viewEndHour : 22
    },
    showMultiDayView : true,
    showMultiWeekView : false,
    showNavJump: false,
    showNavNextPrev: false,
    multiDayViewCfg :
    {
        dayCount : 5,
        startDay: 1,
        startDayIsStatic: true,
        viewStartHour : 8,
        showTime: false,
        viewEndHour : 22,
        getStoreParams: function() {
            var params = this.getStoreDateParams();
            console.log(this.store.getProxy());
            //params.estudioId = this.store.getExtraParam
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
    getStoreParams: function() {
        
    }
});