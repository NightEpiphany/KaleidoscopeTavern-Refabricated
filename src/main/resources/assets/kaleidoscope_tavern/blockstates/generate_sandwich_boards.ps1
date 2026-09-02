$ErrorActionPreference = 'Stop'

$targetDir = $PSScriptRoot
if (-not $targetDir.EndsWith([IO.Path]::DirectorySeparatorChar)) {
    $targetDir += [IO.Path]::DirectorySeparatorChar
}

$boards = [ordered]@{
    'base_sandwich_board.json'            = 'base'
    'grass_sandwich_board.json'           = 'grass'
    'allium_sandwich_board.json'          = 'allium'
    'azure_bluet_sandwich_board.json'     = 'azure_bluet'
    'cornflower_sandwich_board.json'      = 'cornflower'
    'orchid_sandwich_board.json'          = 'orchid'
    'peony_sandwich_board.json'           = 'peony'
    'pink_petals_sandwich_board.json'     = 'pink_petals'
    'pitcher_plant_sandwich_board.json'   = 'pitcher_plant'
    'poppy_sandwich_board.json'           = 'poppy'
    'sunflower_sandwich_board.json'       = 'sunflower'
    'torchflower_sandwich_board.json'     = 'torchflower'
    'tulip_sandwich_board.json'           = 'tulip'
    'wither_rose_sandwich_board.json'     = 'wither_rose'
    'eyeblossom_sandwich_board.json'      = 'eyeblossom'
}

foreach ($fileName in $boards.Keys) {
    $type = $boards[$fileName]
    $variants = [ordered]@{}

    foreach ($half in @('bottom', 'top')) {
        foreach ($rot in 0..15) {
            foreach ($wl in @('false', 'true')) {
                $key = "half=$half,rotation=$rot,waterlogged=$wl"
                $modelFolder = if ($half -eq 'bottom') { 'bottom' } else { $type }
                $variants[$key] = [ordered]@{
                    model = "kaleidoscope_tavern:block/deco/sandwich_board/$modelFolder/rot_$rot"
                }
            }
        }
    }

    $json = [ordered]@{ variants = $variants }
    $content = $json | ConvertTo-Json -Depth 5

    $fullPath = Join-Path $targetDir $fileName
    [IO.File]::WriteAllText($fullPath, $content, (New-Object System.Text.UTF8Encoding($false)))
    Write-Output "Wrote $fullPath"
}
