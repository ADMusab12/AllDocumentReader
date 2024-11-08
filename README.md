# All Doc Viewer App

This is an Android application that allows users to view different types of documents such as PDFs, Word files, PowerPoint presentations, and Excel spreadsheets. The app uses various libraries like Glide, Lottie, and DocViewer to display documents smoothly.

## Features

- View PDF, Word, Excel, and PowerPoint files
- Elegant UI with color-coded appbars based on file type
- File management with external storage access

## Requirements

- Android SDK 29 or higher
- Permissions for reading from external storage

## Installation

- Add this library for document view
```kotlin
// Add DocViewer library
    implementation 'com.github.Victor2018:DocViewer:v3.0.3'
```

- Add permission code 
```kotlin
// Permission check code for getting document from storage

private fun requestStoragePermissions() {
    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
            if (!hasManageExternalStoragePermission()) {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                    data = Uri.parse("package:${applicationContext.packageName}")
                }
                manageExternalStoragePermissionLauncher.launch(intent)
            } else {
                fetchFilesFromStorage()
            }
        }
        else -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_READ_STORAGE)
            } else {
                fetchFilesFromStorage()
            }
        }
    }
}
```

- Syntax of document view 
```kotlin
// Try to open the document
binding.docview.openDoc(this, filePath, 3, -1, false, DocEngine.MICROSOFT)
```


- Status bar color changer
```kotlin
//Extar code for change status bar color
fun Activity.setStatusBarColor(colorResId: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.statusBarColor = ContextCompat.getColor(this, colorResId)

        // Set the light status bar flag for light colors
        if (isColorLight(ContextCompat.getColor(this, colorResId))) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            window.decorView.systemUiVisibility = 0
        }
    }
}

// Helper function to determine if the color is light
private fun isColorLight(color: Int): Boolean {
    val darkness = 1 - (0.299 * ((color shr 16) and 0xFF) +
            0.587 * ((color shr 8) and 0xFF) +
            0.114 * (color and 0xFF)) / 255
    return darkness < 0.5
}
```

## Screenshots

### Screenshot 1: Main Document Viewer Screen
![screenshot](screenshots/main.jpg)

### Screenshot 2: Pdf Viewer Screen
![screenshot](screenshots/pdf.jpg)

### Screenshot 3: Word Viewer Screen
![screenshot](screenshots/word.jpg)

### Screenshot 4: Powerpoint Viewer Screen
![screenshot](screenshots/ppt.jpg)

### Screenshot 4: Excel Viewer Screen
![screenshot](screenshots/excel.jpg)





