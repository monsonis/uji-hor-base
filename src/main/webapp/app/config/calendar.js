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
            items : [
            {
                text : me.editDetailsText,
                iconCls : 'extensible-cal-icon-evt-edit',
                scope: me,
                handler: function(){
                    me.fireEvent('editdetails', me, me.rec, me.ctxEl);
                }
            },
            {
                text : 'Assignar aula',
                iconCls : 'extensible-cal-icon-evt-edit',
                menu : me.dateMenu

            },
            {
                text : 'Assignar a circuit',
                iconCls : 'extensible-cal-icon-evt-edit',
                menu : me.copyMenu

            }, '-',
            {
                text : 'Dividir',
                iconCls : 'extensible-cal-icon-evt-copy',
                scope : me,
                handler : function()
                {
                    Ext.ComponentQuery.query("panelCalendario")[0].fireEvent('eventdivide', me, me.rec);
                }
            },
            {
                text : me.deleteText,
                iconCls : 'extensible-cal-icon-evt-del',
                scope : me,
                handler : function()
                {
                    me.fireEvent('eventdelete', me, me.rec, me.ctxEl);
                }
            } ]
        });
    },

    showForEvent : function(rec, el, xy)
    {
        var me = this;
        me.rec = rec;
        me.ctxEl = el;
        me.showAt(xy);
    }
});

Extensible.calendar.form.EventWindow.override(
{
    getFormItemConfigs : function()
    {
        var items = [
        {
            xtype : 'textfield',
            itemId : this.id + '-title',
            name : Extensible.calendar.data.EventMappings.Title.name,
            fieldLabel : this.titleLabelText,
            anchor : '100%',
            readOnly : true
        },/*
             * { xtype: 'extensible.daterangefield', itemId: this.id + '-dates', name: 'dates',
             * anchor: '95%', singleLine: true, fieldLabel: this.datesLabelText }
             */
        {
            xtype : 'daterangefield',
            itemId : this.id + '-dates',
            name : 'dates',
            anchor : '95%',
            fieldLabel : this.datesLabelText
        } ];

        if (this.calendarStore)
        {
            items.push(
            {
                xtype : 'extensible.calendarcombo',
                itemId : this.id + '-calendar',
                name : Extensible.calendar.data.EventMappings.CalendarId.name,
                anchor : '100%',
                fieldLabel : this.calendarLabelText,
                store : this.calendarStore,
                readOnly : true
            });
        }

        return items;
    },
});

Extensible.calendar.data.EventMappings.Repetir =
{
    name : 'Repetir',
    mapping : 'repetir'
};
Extensible.calendar.data.EventMappings.RepetirCada =
{
    name : 'RepetirCada',
    mapping : 'repetir_cada'
};
Extensible.calendar.data.EventMappings.StartDateRep =
{
    name : 'StartDateRep',
    mapping : 'start_date_rep'
};
Extensible.calendar.data.EventMappings.NoEnd =
{
    name : 'NoEnd',
    mapping : 'no_end'
};
Extensible.calendar.data.EventMappings.EndRepNumber =
{
    name : 'EndRepNumber',
    mapping : 'end_rep_number'
};
Extensible.calendar.data.EventMappings.EndRepNumberComp =
{
    name : 'EndRepNumberComp',
    mapping : 'end_rep_number_comp',
    type : 'int',
    defaultValue : 1
};
Extensible.calendar.data.EventMappings.EndDateRep =
{
    name : 'EndDateRep',
    mapping : 'end_date_rep',
};
Extensible.calendar.data.EventMappings.EndDateRepComp =
{
    name : 'EndDateRepComp',
    mapping : 'end_date_rep_comp',
    type : 'date',
/* dateFormat : 'c' */
};
Extensible.calendar.data.EventModel.reconfigure();

Extensible.calendar.form.EventDetails.override(
{
    // private
    initComponent : function()
    {
        this.addEvents(
        {

            /**
             * @event eventadd Fires after a new event is added
             * @param {Extensible.calendar.form.EventDetails}
             *            this
             * @param {Extensible.calendar.data.EventModel}
             *            rec The new {@link Extensible.calendar.data.EventModel record} that was
             *            added
             */
            eventadd : true,

            /**
             * @event eventupdate Fires after an existing event is updated
             * @param {Extensible.calendar.form.EventDetails}
             *            this
             * @param {Extensible.calendar.data.EventModel}
             *            rec The new {@link Extensible.calendar.data.EventModel record} that was
             *            updated
             */
            eventupdate : true,

            /**
             * @event eventdelete Fires after an event is deleted
             * @param {Extensible.calendar.form.EventDetails}
             *            this
             * @param {Extensible.calendar.data.EventModel}
             *            rec The new {@link Extensible.calendar.data.EventModel record} that was
             *            deleted
             */
            eventdelete : true,

            /**
             * @event eventcancel Fires after an event add/edit operation is canceled by the user
             *        and no store update took place
             * @param {Extensible.calendar.form.EventDetails}
             *            this
             * @param {Extensible.calendar.data.EventModel}
             *            rec The new {@link Extensible.calendar.data.EventModel record} that was
             *            canceled
             */
            eventcancel : true
        });

        this.titleField = Ext.create('Ext.form.TextField',
        {
            fieldLabel : this.titleLabelText,
            name : Extensible.calendar.data.EventMappings.Title.name,
            anchor : '90%',
            readOnly : true
        });
        this.dateRangeField = Ext.create('Event.form.field.DateRange',
        {
            fieldLabel : this.datesLabelText,
            anchor : '90%',
            listeners :
            {
                'change' : Ext.bind(this.onDateChange, this)
            }
        });

        this.repeatField = Ext.create('Ext.form.field.Checkbox',
        {
            boxLabel : 'Repetir',
            name : Extensible.calendar.data.EventMappings.Repetir.name,
            listeners :
            {
                'change' :
                {
                    fn : function()
                    {
                        if (this.repeatField.getValue())
                        {
                            this.dateRepeatField.show();
                        }
                        else
                        {
                            this.dateRepeatField.hide();
                        }
                    },
                    scope : this
                },
            }
        });
        this.dateRepeatField = Ext.create('Event.form.field.DateRepeat',
        {
            anchor : '90%'
        });

        var leftFields = [ this.titleField, this.dateRangeField, this.repeatField, this.dateRepeatField ];

        if (this.calendarStore)
        {
            this.calendarField = Ext.create('Extensible.calendar.form.field.CalendarCombo',
            {
                store : this.calendarStore,
                fieldLabel : this.calendarLabelText,
                name : Extensible.calendar.data.EventMappings.CalendarId.name,
                anchor : '90%',
                readOnly : true
            });
            leftFields.splice(2, 0, this.calendarField);
        }
        ;

        this.items = [
        {
            id : this.id + '-left-col',
            columnWidth : this.colWidthLeft,
            layout : 'anchor',
            fieldDefaults :
            {
                labelWidth : this.labelWidth
            },
            border : false,
            items : leftFields
        } /*
             * , { id : this.id + '-right-col', columnWidth : this.colWidthRight, layout : 'anchor',
             * fieldDefaults : { labelWidth : this.labelWidthRightCol || this.labelWidth }, border :
             * false, items : rightFields }
             */];

        this.fbar = [
        {
            text : this.saveButtonText,
            scope : this,
            handler : this.onSave
        },
        {
            itemId : this.id + '-del-btn',
            text : this.deleteButtonText,
            scope : this,
            handler : this.onDelete
        },
        {
            text : this.cancelButtonText,
            scope : this,
            handler : this.onCancel
        } ];

        this.addCls('ext-evt-edit-form');

        // this.callParent(arguments);
        this.superclass.initComponent.call(this);
    },

    loadRecord : function(rec)
    {
        var me = this, EventMappings = Extensible.calendar.data.EventMappings;
        
        // Simulamos datos en los nuevos campos
        //rec.data[EventMappings.Repetir.name] = "on";
        //rec.data[EventMappings.EndRepNumberComp.name] = 6;
        //rec.data[EventMappings.EndDateRepComp.name] = '7/9/2012';

        me.form.reset().loadRecord.apply(me.form, arguments);
        me.activeRecord = rec;
        me.dateRangeField.setValue(rec.data);

        if (me.recurrenceField)
        {
            me.recurrenceField.setStartDate(rec.data[EventMappings.StartDate.name]);
            me.recurrenceField.setValue(rec.data[EventMappings.RRule.name]);

            if (!rec.data[EventMappings.RInstanceStartDate.name])
            {
                // If the record is new we have to set the instance start date explicitly to match
                // the
                // field's default so that it does not show up later as dirty if it is not edited:
                rec.data[EventMappings.RInstanceStartDate.name] = rec.getStartDate();
            }
        }

        if (me.calendarField)
        {
            me.calendarField.setValue(rec.data[EventMappings.CalendarId.name]);
        }

        if (rec.phantom)
        {
            me.setTitle(me.titleTextAdd);
            me.down('#' + me.id + '-del-btn').hide();
        }
        else
        {
            me.setTitle(me.titleTextEdit);
            me.down('#' + me.id + '-del-btn').show();
        }

        // Using setValue() results in dirty fields, so we reset the field state
        // after loading the form so that the current values are the "original" values
        me.form.getFields().each(function(item)
        {
            item.resetOriginalValue();
        });

        me.titleField.focus();

        // Activar los radios del componente de repetición
        if (rec.data[EventMappings.Repetir.name])
        {
            if (rec.data[EventMappings.EndDateRepComp.name])
            {
                 this.dateRepeatField.down('radiodatefield radio').setValue(true);
            }
            else if (rec.data[EventMappings.EndRepNumberComp.name])
            {
                this.dateRepeatField.down('radionumberfield radio').setValue(true);
            }
        }
    }

});
