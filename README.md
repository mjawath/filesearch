# Developer Check-In #1

This is a technical check-in for Software Engineers.

# Problem Statement
It's common to see `TODO`s in code. It's also common for `TODO`s to remain as to-dos for a long time. One way we can solve this problem is to have a service that runs through all files in a given directory and checks for any instances of the key-phrase `"TODO"`, flagging each one of them out for humans to continue working on them.

# Challenge
Write an application/script that when executed in a directory, produces a list of all files (using their absolute paths) containing the keyword `"TODO"` in them. The files can be in the immediate directory, or a sub-directory (or a sub-directory of the sub-directory, ad infinitum).

# Example
Given the following directory structure:

```
/path/to/your/dir
  - somedir
    - somemodule
      - somefile.js
      - someotherfile.js
  - somedir2
    - anotherdir
      - yet_another_dir
        - index.js
      - index.js
    - index.js
  - somedir3
    - another_file.js
```

Assuming your application runs at `/path/to/your/dir`, and assuming all of the files contains the text string `"TODO"`, the application should output something similar to:

```
/path/to/your/dir/somedir/somemodule/somefile.js
/path/to/your/dir/somedir/somemodule/someotherfile.js
/path/to/your/dir/somedir2/anotherdir/yet_another_dir/index.js
/path/to/your/dir/somedir2/anotherdir/index.js
/path/to/your/dir/somedir2/index.js
/path/to/your/dir/somedir3/another_file.js
```

# Deliverables
1. Link to code repository
2. README.md with full instructions to achieve the above functionality
3. Tests

# Constraints/Other Things
- You are free to publish/open-source your solution but please do not link back to this particular README.md 🙏🏼 (you are however free to use this in your own capacity according to [the current license](#license))
- No third party libraries may be used for the code logic though you may use them in your tooling (if any) or tests
- No calls to the system shell should be used (keep it cross-platform compatible!)
- There is no time constraint for this exercise, take as long/short as you need/want to - ie **impress us in your own way**!
- We use Node.js/Golang primarily - while it'd be nice if you implemented it in any of the two, you are **highly recommended to use the language you are strongest in/most fitting of this assignment**.
- Your submitted code will be worked on during a pair programming session during an on-site interview should you be selected so... Don't copy and paste too much 😉

# Cheers and all the best!

# License

Content is licensed under the [Creative Commons 4.0 (Non-Commercial, Attribution) license](https://creativecommons.org/licenses/by-nc-sa/4.0/) **AFTER** you've completed it.
