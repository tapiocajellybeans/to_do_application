# 📋 To Do App — Android To-Do App

A feature-rich Android to-do list application built with Java in Android Studio. Manage your tasks with due dates, a calendar view, daily reminders, and a home screen widget.

---

## ✨ Features

- **Add / Edit / Delete Tasks** — Create tasks with a name and due date via a bottom sheet dialog. Swipe right to edit, swipe left to delete.
- **Due Date Picker** — Choose a due date from a date picker (past dates are disabled).
- **Task Sorting** — Tasks are automatically sorted by due date (ascending).
- **Check Off Tasks** — Mark tasks as complete using a checkbox; completed tasks are removed from the list.
- **Calendar View** — Browse tasks by month and tap any day to see tasks due on that date.
- **Daily Reminder Notifications** — Set a daily alarm to receive a push notification listing your pending tasks.
- **Home Screen Widget** — Add a widget to your home screen showing all current tasks. Tap "Save to Widget" in the app to refresh it.
- **Splash Screen** — Animated splash screen with sound effects on launch.

---

## 🛠️ Tech Stack

| Component | Technology |
|---|---|
| Language | Java |
| UI | XML Layouts, RecyclerView, CardView |
| Database | SQLite via `SQLiteOpenHelper` |
| Notifications | `AlarmManager` + `NotificationCompat` |
| Widget | `AppWidgetProvider` + `SharedPreferences` |
| Bottom Sheets | `BottomSheetDialogFragment` (Material Components) |
| Calendar | `LocalDate` / `YearMonth` (API 26+) |
| Time Picker | `MaterialTimePicker` |

---

## 📁 Project Structure

```
app/src/main/java/com/example/todoexample/
│
├── MainActivity.java              # Main task list screen
├── AddNewTask.java                # Bottom sheet to add/edit tasks
├── Settings.java                  # Bottom sheet for notification settings
├── RecyclerItemTouchHelper.java   # Swipe-to-edit / swipe-to-delete
├── OnSwipeTouchListener.java      # Gesture detector for calendar swipe
├── DialogCloseListener.java       # Interface to refresh list on dialog dismiss
├── DestinationActivity.java       # Target activity for notification tap
├── splashactivitiy.java           # Splash screen with animations & sounds
├── ToDoWidget.java                # Home screen widget provider
│
├── model/
│   └── todomodel.java             # Task data model (id, task, date_due, status)
│
├── adapter/
│   ├── todoadapter.java           # RecyclerView adapter for main task list
│   └── date_dailyadapter.java     # RecyclerView adapter for daily calendar view
│
├── calendar/
│   ├── CalendarMainActivity.java  # Monthly calendar view
│   ├── CalendarAdapter.java       # Grid adapter for calendar cells
│   ├── CalendarViewHolder.java    # ViewHolder for calendar cells
│   └── CalendarDailyView.java     # Daily task list for a selected date
│
├── Utils/
│   └── DatabaseHandler.java       # SQLite CRUD operations
│
└── receiver/
    ├── AlarmReceiver.java         # BroadcastReceiver for daily notifications
    └── updateWidget.java          # BroadcastReceiver to refresh the widget
```

---

## 🚀 Getting Started

### Prerequisites

- Android Studio (Flamingo or newer recommended)
- Android SDK API 26+ (Android 8.0 Oreo) — required for calendar features
- A physical device or emulator

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/tapiocajellybeans/to_do_application.git
   ```
2. Open the project in Android Studio.
3. Let Gradle sync and resolve dependencies.
4. Run the app on a device or emulator (API 26+).

---

## 📱 Usage

| Action | How |
|---|---|
| Add a task | Tap the **+** FAB → enter name and due date → Save |
| Edit a task | Swipe a task **right** |
| Delete a task | Swipe a task **left** → confirm |
| Complete a task | Tick the checkbox — task is removed automatically |
| Browse by date | Tap the calendar icon → select a day |
| Set daily reminder | Tap the settings icon → pick a time → Save |
| Refresh widget | Tap the widget icon in the toolbar |

---

## ⚠️ Known Issues / Notes

- The `DestinationActivity` launched by notification taps is currently a placeholder with no content.
- Minimum supported API is **26** due to use of `java.time.LocalDate`.
- `PendingIntent` flags may need updating for API 31+ (`FLAG_IMMUTABLE` required).
