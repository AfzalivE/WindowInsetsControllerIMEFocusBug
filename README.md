# WindowInsetsControllerIMEFocusBug
This sample app demonstrates a weird behavior with WindowInsetsController and IME focus.

Without first calling `requestFocus()` and `showSoftInput()` first like below, `WindowInsetsController#show(WindowInsets.Type.ime())` stops working if an `EditText` with focus is removed from the view hierarchy.

```
editText.requestFocus()
getSystemService<InputMethodManager>()?.showSoftInput(editText, 0)
```

This is also valid for when `WindowInsetsController#controlWindowInsetsAnimation()` is called. One scenario where you might encounted this is if you have a fragment enter transition, that you postpone until the IME is about to show up. In this case, if the fragment that contains an `EditText` that requests focus is dismissed after the focus change, it will not re-appear since the IME won't show up. 

Here's a much more simplistic app recreating the same effect.


https://user-images.githubusercontent.com/436057/120440007-270f1800-c351-11eb-8bed-ad15f24ded9d.mov

