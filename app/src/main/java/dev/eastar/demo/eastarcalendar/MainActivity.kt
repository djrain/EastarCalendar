package dev.eastar.demo.eastarcalendar

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import dev.eastar.calendar.CalendarPagerFragment
import dev.eastar.calendar.day
import dev.eastar.calendar.month
import dev.eastar.calendar.tools.DayDrawerImpl
import dev.eastar.calendar.tools.MonthDrawerImpl
import dev.eastar.calendar.tools.WeekDrawerImpl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val calendarPager: CalendarPagerFragment = Fragment.instantiate(this, CalendarPagerFragment::class.java.name, null) as CalendarPagerFragment
        prev.setOnClickListener { calendarPager.prev() }
        next.setOnClickListener { calendarPager.next() }
        month.setOnClickListener { calendarPager.move() }

        calendarPager.setOnChangeMonthListener { month.text = it.month }
        calendarPager.setOnChangeSelectedDayListener { toast(it.day) }
        calendarPager.setOnWeekClickListener {
            toast(Calendar.getInstance().apply { set(Calendar.DAY_OF_WEEK, it) }.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()))
        }
        calendarPager.setDayDrawer(object : DayDrawerImpl() {
            override fun draw(canvas: Canvas, rc: Rect, day: Long, displayMonth: Long, selectedDay: Long, pressedDay: Long) {
                super.draw(canvas, rc, day, displayMonth, selectedDay, pressedDay)
            }

            override fun drawDay(canvas: Canvas, rc: Rect, day: Long, displayMonth: Long, selectedDay: Long) {
                super.drawDay(canvas, rc, day, displayMonth, selectedDay)
                canvas.drawRoundRect(RectF(rc).apply { inset(10F, 10F) }, 3F, 3F, Paint().apply { color = Color.parseColor("#550000ff") })
            }

            override fun drawState(canvas: Canvas, rc: Rect, day: Long, displayMonth: Long, selectedDay: Long) {
                super.drawState(canvas, rc, day, displayMonth, selectedDay)
            }

            override fun drawPressed(canvas: Canvas, rc: Rect, day: Long, displayMonth: Long, selectedDay: Long, pressedDay: Long) {
                canvas.drawRoundRect(RectF(rc).apply { inset(1F, 1F) }, 3F, 3F, Paint().apply { color = Color.parseColor("#55ff0000") })
                super.drawPressed(canvas, rc, day, displayMonth, selectedDay, pressedDay)
            }
        })
        calendarPager.setWeekDrawer(object : WeekDrawerImpl() {
        })
        calendarPager.setMonthDrawer(object : MonthDrawerImpl() {
        })
        supportFragmentManager.beginTransaction().replace(R.id.container, calendarPager).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

fun Context.toast(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()