cd src/app
del /S *.map
del /S *.js
xcopy C:\Users\shaurya\workspace\fin-cal\ui\src C:\Users\shaurya\workspace\fin-cal\ui\out /s /e /Y

xcopy C:\Users\shaurya\workspace\fin-cal\ui\src\app C:\Users\shaurya\workspace\fin-cal\web\src\app /s /e /Y
