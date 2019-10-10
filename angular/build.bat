cmd /c ng build --prod --base-href /
@rem cmd /c ng build --base-href /

cmd /c xcopy  ".\dist\user" "..\..\..\java\vol-3\user\src\main\webapp" /s /e /y /i
@rem cmd /c xcopy  ".\dist\user" "..\..\..\java\user\src\main\webapp" /s /e /y /i