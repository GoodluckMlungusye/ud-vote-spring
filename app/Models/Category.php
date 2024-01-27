<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Relations\HasMany;

class Category extends Model
{
    use HasFactory;

    protected $guarded = [];


    public function contestants(): HasMany
    {
        return $this->hasMany(Contestant::class);
    }


    public function voterContestant(): HasMany
    {
        return $this->hasMany(VoterContestant::class);
    }



}
