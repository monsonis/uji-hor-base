Ext.define('HOR.view.horarios.PanelCalendarioDetalle',
{
    extend : 'Extensible.calendar.CalendarPanel',
    alias : 'widget.panelCalendarioDetalle',
    region : 'center',
    title : 'Calendari detall',
    depends : [ 'HOR.store.StoreCalendarios', 'HOR.store.StoreEventosDetalle' ],
    calendarStore : Ext.create('HOR.store.StoreCalendarios'),
    eventStore : Ext.create('HOR.store.StoreEventosDetalle'),
    editModal : true,
    readOnly : false,
    flex : 1,
    padding : 5,
    showMultiDayView : true,
    showMultiWeekView : true,
    showMonthView : false,
    showWeekView : false,
    activeItem : 1,
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
    multiWeekViewCfg :
    {
        weekCount : 4,
        showTime : false,
        showMonth : true

    },
    limpiaCalendario : function()
    {
        this.store.removeAll(false);
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
        
        this.desactivaMenuContextualYEdicionDetalle();
    },

    desactivaMenuContextualYEdicionDetalle : function()
    {
        Extensible.calendar.menu.Event.override(
        {
            buildMenu : function()
            {
                var me = this;

                if (me.rendered)
                {
                    return;
                }
                Ext.apply(me,
                {
                    items : []
                });
            },

            showForEvent : function(rec, el, xy)
            {

            }
        });

        Extensible.calendar.form.EventWindow.override(
        {
            getFooterBarConfig : function()
            {
                var cfg = [ '->',
                {
                    text : this.saveButtonText,
                    itemId : this.id + '-save-btn',
                    disabled : false,
                    handler : this.onSave,
                    scope : this
                },
                {
                    text : this.deleteButtonText,
                    itemId : this.id + '-delete-btn',
                    disabled : true,
                    handler : this.onDelete,
                    scope : this,
                    hideMode : 'offsets' // IE requires this
                },
                {
                    text : this.cancelButtonText,
                    itemId : this.id + '-cancel-btn',
                    disabled : false,
                    handler : this.onCancel,
                    scope : this
                } ];

                if (this.enableEditDetails !== false)
                {
                    cfg.unshift(
                    {
                        xtype : 'tbtext',
                        itemId : this.id + '-details-btn',
                        text : '<a href="#" class="' + this.editDetailsLinkClass + '">' + this.detailsLinkText + '</a>'
                    });
                }
                return cfg;

            }
        });
    },

    getMultiDayText : function()
    {
        return 'Setmana';
    },

    getMultiWeekText : function()
    {
        return 'Mes';
    },

    onStoreUpdate : function()
    {
    }
});