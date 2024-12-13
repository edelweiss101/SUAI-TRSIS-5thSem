@echo off
setlocal

:: Путь к входному файлу
set input_file=sec_sys.html
:: Путь к выходному файлу
set output_file=out.txt

:: Выполнение поиска с использованием PowerShell
powershell -NoProfile -Command ^
    "Get-Content '%input_file%' | ForEach-Object { if ($_ -match '><!-- (.*?) -->') { 'sec_sys.=' + $matches[1] } } | Set-Content '%output_file%'"

echo Выполнение завершено. Результаты сохранены в %output_file%.
