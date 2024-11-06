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


### 3. **Adding Code Snippets with Copy Option**
To add code snippets with the "copy" option for users, you can use the `fence` (code block) syntax in Markdown. Here’s how you can do it:

```markdown

// Inside an Activity
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



// Try to open the document
binding.docview.openDoc(this, filePath, 3, -1, false, DocEngine.MICROSOFT)

