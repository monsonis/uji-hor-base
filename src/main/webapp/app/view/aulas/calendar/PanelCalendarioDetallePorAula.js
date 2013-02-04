Ext.define('HOR.view.aulas.calendar.PanelCalendarioDetallePorAula',
{
    extend : 'Extensible.calendar.CalendarPanel',
    alias : 'widget.panelCalendarioDetallePorAula',    
    region : 'center',
    title : 'Ocupació Aula',
    depends : [ 'HOR.store.StoreCalendarios', 'HOR.store.StoreAulasDetalle' ],
    calendarStore : Ext.create('HOR.store.StoreCalendarios'),
    eventStore : Ext.create('HOR.store.StoreAulasDetalle'),
    editModal : true,
    readOnly : true,
    flex : 1,
    padding : 5,
    showMultiDayView : true,
    showMultiWeekView : true,
    showMonthView : false,
    showWeekView : false,
    activeItem : 1,
    viewConfig :
    {
        viewStartHour : 8,
        viewEndHour : 22
    },
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
            params.aulaId = this.store.getProxy().extraParams['aulaId'];
            params.semestreId = this.store.getProxy().extraParams['semestreId'];
            return params;
        }
    },
    multiWeekViewCfg :
    {
        weekCount : 4,       
        showTime : false,
        showMonth : true
       
    },
    limpiaCalendario : function()
    {
        this.store.removeAll(false);
        this.setTitle('Ocupació Aula');
    },
    initComponent : function()
    {
        Extensible.calendar.template.BoxLayout.override(
        {
            firstWeekDateFormat : 'D j',
            multiDayFirstDayFormat : 'M j, Y',
            multiDayMonthStartFormat : 'M j'
        });

        this.callParent(arguments);
    },
    getMultiDayText : function()
    {
        return 'Setmana';
    },
    
    getMultiWeekText : function()
    {
        return 'Mes';
    },

    onStoreUpdate: function() {
    }
});