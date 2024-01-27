<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\HasMany;

class College extends Model
{
    use HasFactory;

    protected $guarded = [];




    public function voters(): HasMany
    {
        return $this->hasMany(Voter::class);
    }



    public function years(): HasMany

    {
        return $this->hasMany(CollegeYear::class);
    }


}
